package com.dvynokurov.service;

import com.dvynokurov.model.*;
import com.dvynokurov.repository.GameRepository;
import com.dvynokurov.util.exceptions.ColumnFullException;
import com.dvynokurov.util.exceptions.GameDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class GameService {

    @Value("${game.grid.width}")
    private int gridWidth;

    @Value("${game.grid.height}")
    private int gridHeight;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GridGenerationService gridGenerationService;

    @Autowired
    AIPlayer aiPlayer;

    @Autowired
    GameFinishedCheckingService gameFinishedCheckingService;

    private ConcurrentMap<UUID, ReentrantLock> gameLocks = new ConcurrentHashMap<>();

    public Game createNewGame(){
        UUID uuid = UUID.randomUUID();
        Grid grid = gridGenerationService.generateGrid(gridWidth, gridHeight);
        Game game = new Game(uuid, grid, GameStatus.IN_PROGRESS);
        gameRepository.save(game);
        gameLocks.put(uuid, new ReentrantLock());
        return game;
    }

    public Game performPlayerMove(UUID gameId, int columnNumber){
        ReentrantLock lock = gameLocks.get(gameId);
        if(lock==null) throw new GameDoesNotExistException();
        try {
            lock.lock();
            return safelyPerformPlayerMove(gameId, columnNumber);
        }finally {
            lock.unlock();
        }
    }

    private Game safelyPerformPlayerMove(UUID gameId, int firstPlayerMove) {
        Game game = gameRepository.findOneById(gameId);
        Grid grid = game.getGrid();
        updateAndPersistGame(firstPlayerMove, game, Player.FIRST);
        if(gameFinishedCheckingService.checkFinished(grid, Player.FIRST)) {
            game.setGameStatus(GameStatus.FIRST_PLAYER_WON);
        }else{
            int secondPlayerMove = aiPlayer.getComputerMove(grid);
            updateAndPersistGame(secondPlayerMove, game, Player.SECOND);
            if (gameFinishedCheckingService.checkFinished(grid, Player.SECOND)) {
                game.setGameStatus(GameStatus.SECOND_PLAYER_WON);
            }
        }
        return game;
    }

    private void updateAndPersistGame(int move, Game game, Player player) {
        putDiskToColumn(game.getGrid(), move, player);
        setLastMove(game, player, move);
        gameRepository.save(game);
    }

    private void setLastMove(Game game, Player player, int move) {
        if (player.equals(Player.FIRST)) {
            game.setFirstPlayerLastMove(move);
        }else {
            game.setSecondPlayerLastMove(move);
        }
    }

    private void putDiskToColumn(Grid grid, int columnIndex, Player player) {
        Assert.isTrue(columnIndexIsValid(columnIndex), "Column index is out of range");
        Assert.notNull(player, "Owner should be playable");

        Column column = grid.getColumns().get(columnIndex);
        putDisc(column, player);
    }

    private boolean columnIndexIsValid(int columnIndex) {
        return columnIndex >= 0 && columnIndex <= gridWidth;
    }

    private Cell getFirstFreeCell(Column column){
        for (Cell cell : column.getCells()) {
            if(cell.getOwner()==null) return cell;
        }
        throw new ColumnFullException();
    }

    private void putDisc(Column column, Player player) {
        Cell firstFreeCell = getFirstFreeCell(column);
        firstFreeCell.setOwner(player);
    }

    public Game getGame(UUID gameId) {
        return gameRepository.findOneById(gameId);
    }
}
