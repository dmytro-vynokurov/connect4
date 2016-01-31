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
}
