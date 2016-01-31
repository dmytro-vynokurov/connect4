package com.dvynokurov.model;

import java.util.UUID;

public class Game {
    private UUID id;
    private Grid grid;

    public Game(UUID id, Grid grid) {
        this.id = id;
        this.grid = grid;
    }

    public UUID getId() {
        return id;
    }

    public Grid getGrid() {
        return grid;
    }
}
