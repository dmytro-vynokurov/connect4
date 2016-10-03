package com.dvynokurov.model;

import java.util.UUID;

public class Game {
    private UUID id;
    private GameStatus gameStatus;
    private Move firstPlayerLastMove;
    private Move secondPlayerLastMove;
    private Grid grid;

    public Game(UUID id, Grid grid, GameStatus gameStatus) {
        this.id = id;
        this.grid = grid;
        this.gameStatus = gameStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Move getFirstPlayerLastMove() {
        return firstPlayerLastMove;
    }

    public void setFirstPlayerLastMove(Move firstPlayerLastMove) {
        this.firstPlayerLastMove = firstPlayerLastMove;
    }

    public Move getSecondPlayerLastMove() {
        return secondPlayerLastMove;
    }

    public void setSecondPlayerLastMove(Move secondPlayerLastMove) {
        this.secondPlayerLastMove = secondPlayerLastMove;
    }

    public Grid getGrid() {
        return grid;
    }
}
