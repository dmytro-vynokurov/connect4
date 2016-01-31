package com.dvynokurov.service;

import com.dvynokurov.model.Game;
import com.dvynokurov.model.GameStatus;
import com.dvynokurov.model.Grid;
import com.dvynokurov.model.Player;
import com.dvynokurov.repository.GameRepository;
import com.dvynokurov.util.exceptions.GameDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    GridFillingService gridFillingService;

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
            Game game = safelyPerformPlayerMove(gameId, columnNumber);
            if(game.getGameStatus()!=GameStatus.IN_PROGRESS) gameLocks.remove(game.getId());
            return game;
        }finally {
            lock.unlock();
        }
    }

    private Game safelyPerformPlayerMove(UUID gameId, int firstPlayerMove) {
        Game game = gameRepository.findOneById(gameId);
        Grid grid = game.getGrid();
        updateGame(firstPlayerMove, game, Player.FIRST);
        if(gameFinishedCheckingService.checkFinished(grid, Player.FIRST)) {
            game.setGameStatus(GameStatus.FIRST_PLAYER_WON);
        }else{
            int secondPlayerMove = aiPlayer.getComputerMove(grid);
            updateGame(secondPlayerMove, game, Player.SECOND);
            if (gameFinishedCheckingService.checkFinished(grid, Player.SECOND)) {
                game.setGameStatus(GameStatus.SECOND_PLAYER_WON);
            }
        }
        gameRepository.save(game);
        return game;
    }

    private void updateGame(int move, Game game, Player player) {
        gridFillingService.putDiskToColumn(game.getGrid(), move, player);
        setLastMove(game, player, move);
    }

    private void setLastMove(Game game, Player player, int move) {
        if (player.equals(Player.FIRST)) {
            game.setFirstPlayerLastMove(move);
        }else {
            game.setSecondPlayerLastMove(move);
        }
    }

    public Game getGame(UUID gameId) {
        Game oneById = gameRepository.findOneById(gameId);
        if(oneById==null) throw new GameDoesNotExistException();
        return oneById;
    }
}
