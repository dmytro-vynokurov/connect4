package com.dvynokurov.service;

import com.dvynokurov.model.Cell;
import com.dvynokurov.model.Column;
import com.dvynokurov.model.Grid;
import com.dvynokurov.model.Player;
import com.dvynokurov.util.exceptions.ColumnFullException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GridFillingService {

    public void putDiskToColumn(Grid grid, int columnIndex, Player player) {
        Assert.isTrue(columnIndexIsValid(columnIndex, grid), "Column index is out of range");
        Assert.notNull(player, "Owner should be playable");

        Column column = grid.getColumns().get(columnIndex);
        putDisc(column, player);
    }

    private boolean columnIndexIsValid(int columnIndex, Grid grid) {
        return columnIndex >= 0 && columnIndex <= grid.getColumns().size();
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
}
