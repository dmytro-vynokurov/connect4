package com.dvynokurov.model;

import java.util.List;

public class Column {
    private List<Cell> cells;

    public Column(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
