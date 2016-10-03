package com.dvynokurov.service;

import com.dvynokurov.model.Cell;
import com.dvynokurov.model.Column;
import com.dvynokurov.model.Grid;
import com.dvynokurov.model.Move;
import com.dvynokurov.model.Player;
import com.dvynokurov.util.exceptions.ColumnFullException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GridFillingService {

    public Move putDiskToColumn(Grid grid, int columnIndex, Player player) {
        Assert.isTrue(columnIndexIsValid(columnIndex, grid), "Column index is out of range");
        Assert.notNull(player, "Owner should be playable");

        Column column = grid.getColumns().get(columnIndex);
        int firstFreeCellIndex = getFirstFreeCellIndex(column);
        Cell firstFreeCell = column.getCells().get(firstFreeCellIndex);
        firstFreeCell.setOwner(player);
        return new Move(firstFreeCellIndex, columnIndex);
    }

    public boolean columnHasEmptyCells(Grid grid, int columnIndex){
        Column column = grid.getColumns().get(columnIndex);
        Cell firstFreeCell = getFirstFreeCell(column);
        return firstFreeCell != null;
    }

    private boolean columnIndexIsValid(int columnIndex, Grid grid) {
        return columnIndex >= 0 && columnIndex <= grid.getColumns().size();
    }

    private Cell getFirstFreeCell(Column column){
        final int firstFreeCellIndex = getFirstFreeCellIndex(column);
        return column.getCells().get(firstFreeCellIndex);
    }

    private int getFirstFreeCellIndex(Column column) {
        for (int i = 0; i < column.getCells().size(); i++) {
            final Cell cell = column.getCells().get(i);
            if(cell.getOwner()==null) return i;
        }
        throw new ColumnFullException();
    }

}
