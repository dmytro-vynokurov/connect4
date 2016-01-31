package com.dvynokurov.model;

import java.util.List;

public class Grid {
    private List<Column> columns;

    public Grid(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public int getWidth() {
        return getColumns().size();
    }

    public int getHeight() {
        return getColumns().get(0).getCells().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        return !(columns != null ? !columns.equals(grid.columns) : grid.columns != null);

    }

    @Override
    public int hashCode() {
        return columns != null ? columns.hashCode() : 0;
    }
}
