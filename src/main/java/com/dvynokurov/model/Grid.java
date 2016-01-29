package com.dvynokurov.model;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private List<Column> columns;

    public Grid(int numberOfColumns, int columnSize) {
        this.columns = new ArrayList<>(numberOfColumns);
        for (int i = 0; i < numberOfColumns; i++) {
            columns.add(new Column(columnSize, i));
        }
    }

    public boolean putDiskToColumn(int columnIndex, Owner owner) {
        Assert.isTrue(columnIndexIsValid(columnIndex), "Column index is out of range");
        Assert.isTrue(ownerIsPlayable(owner), "Owner should be playable");

        Column column = columns.get(columnIndex);
        return column.putDisc(owner);
    }

    private boolean columnIndexIsValid(int columnIndex) {
        return columnIndex >= 0 && columnIndex <= columns.size();
    }

    private boolean ownerIsPlayable(Owner owner){
        return !owner.equals(Owner.EMPTY);
    }
}
