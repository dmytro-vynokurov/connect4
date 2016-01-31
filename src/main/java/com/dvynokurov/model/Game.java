package com.dvynokurov.model;

import java.util.UUID;

public class Game {
    private UUID id;
    private Grid grid;
    private GameStatus gameStatus;

    public Game(UUID id, Grid grid, GameStatus gameStatus) {
        this.id = id;
        this.grid = grid;
        this.gameStatus = gameStatus;
    }

    public UUID getId() {
        return id;
    }

    public Grid getGrid() {
        return grid;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
