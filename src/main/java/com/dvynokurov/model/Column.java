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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        return !(cells != null ? !cells.equals(column.cells) : column.cells != null);

    }

    @Override
    public int hashCode() {
        return cells != null ? cells.hashCode() : 0;
    }
}
