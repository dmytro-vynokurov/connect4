package com.dvynokurov.service;

import com.dvynokurov.model.*;
import com.dvynokurov.repository.GameRepository;
import com.dvynokurov.web.dto.GameIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

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

    public GameIdDto createNewGame(){
        UUID uuid = UUID.randomUUID();
        Grid grid = gridGenerationService.generateGrid(gridWidth, gridHeight);
        Game game = new Game(uuid, grid);
        gameRepository.save(game);
        return new GameIdDto(uuid.toString());
    }

    public boolean putDiskToColumn(Grid grid, int columnIndex, Owner owner) {
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

    public boolean putDisc(Column column, Owner owner) {
        Cell firstFreeCell = getFirstFreeCell(column);
        if(firstFreeCell == null) {
            return false;
        } else {
            firstFreeCell.setOwner(owner);
            return true;
        }
    }

}
