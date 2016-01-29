package com.dvynokurov.model;

import java.util.UUID;

public class Game {
    private UUID id;
    private Grid grid;

    public Game(UUID id, int numberOfColumns, int columnSize) {
        this.id = id;
        this.grid = new Grid(numberOfColumns, columnSize);
    }

    public UUID getId() {
        return id;
    }

    public Grid getGrid() {
        return grid;
    }
}
