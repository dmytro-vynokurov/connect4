package com.dvynokurov.service;

import com.dvynokurov.model.*;
import com.dvynokurov.repository.GameRepository;
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

    private Game safelyPerformPlayerMove(UUID gameId, int columnNumber) {
        Game game = gameRepository.findOneById(gameId);
        putDiskToColumn(game.getGrid(), columnNumber, Owner.FIRST);
        int computerMove = aiPlayer.getComputerMove(game.getGrid());
        putDiskToColumn(game.getGrid(), computerMove, Owner.SECOND);
        gameRepository.save(game);
        return game;
    }


    private boolean putDiskToColumn(Grid grid, int columnIndex, Owner owner) {
        Assert.isTrue(columnIndexIsValid(columnIndex), "Column index is out of range");
        Assert.isTrue(ownerIsPlayable(owner), "Owner should be playable");

        Column column = grid.getColumns().get(columnIndex);
        return putDisc(column, owner);
    }

    private boolean columnIndexIsValid(int columnIndex) {
        return columnIndex >= 0 && columnIndex <= gridWidth;
    }

    private boolean ownerIsPlayable(Owner owner){
        return !owner.equals(Owner.EMPTY);
    }


    private Cell getFirstFreeCell(Column column){
        for (Cell cell : column.getCells()) {
            if(cell.getOwner().equals(Owner.EMPTY)) return cell;
        }
        return null;
    }

    private boolean putDisc(Column column, Owner owner) {
        Cell firstFreeCell = getFirstFreeCell(column);
        if(firstFreeCell == null) {
            return false;
        } else {
            firstFreeCell.setOwner(owner);
            return true;
        }
    }

}
