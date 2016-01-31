package com.dvynokurov.service;

import com.dvynokurov.model.Cell;
import com.dvynokurov.model.Column;
import com.dvynokurov.model.Grid;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class GridGenerationService {

    public Grid generateGrid(int numberOfColumns, int columnSize) {
        Assert.isTrue(columnSize > 0, "Column size should be positive");
        Assert.isTrue(numberOfColumns > 0, "Number of columns should be positive");

        List<Column> columns = createColumnsWithEmptyCells(numberOfColumns, columnSize);
        return new Grid(columns);
    }

    private List<Column> createColumnsWithEmptyCells(int numberOfColumns, int columnSize) {
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < numberOfColumns; i++) {
            List<Cell> cells = createListOfEmptyCells(columnSize);
            Column column = new Column(cells);
            columns.add(column);
        }
        return columns;
    }

    private List<Cell> createListOfEmptyCells(int columnSize) {
        List<Cell> cells = new ArrayList<>(columnSize);
        for (int i = 0; i < columnSize; i++) {
            cells.add(new Cell());
        }
        return cells;
    }

}
