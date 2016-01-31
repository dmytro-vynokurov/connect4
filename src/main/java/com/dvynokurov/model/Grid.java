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
}
