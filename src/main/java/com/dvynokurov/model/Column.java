package com.dvynokurov.model;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private int index;
    private List<Cell> cells;

    public Column(int columnSize, int index) {
        this.index = index;
        this.cells = new ArrayList<>(columnSize);
        for (int i = 0; i < columnSize; i++) {
            cells.add(new Cell(i));
        }
    }

    public int getIndex() {
        return index;
    }

    private Cell getFirstFreeCell(){
        for (Cell cell : cells) {
            if(cell.getOwner().equals(Owner.EMPTY)) return cell;
        }
        return null;
    }

    public boolean putDisc(Owner owner) {
        Cell firstFreeCell = getFirstFreeCell();
        if(firstFreeCell == null) {
            return false;
        } else {
            firstFreeCell.setOwner(owner);
            return true;
        }
    }
}
