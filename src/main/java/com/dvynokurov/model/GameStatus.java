package com.dvynokurov.model;

public enum GameStatus {
    IN_PROGRESS, FIRST_PLAYER_WON, SECOND_PLAYER_WON;

    public static GameStatus getStatusForWinner(Player winner) {
        if(winner==null) return IN_PROGRESS;
        switch (winner) {
            case FIRST: return FIRST_PLAYER_WON;
            case SECOND: return SECOND_PLAYER_WON;
            default: throw new IllegalArgumentException("Only two players supported");
        }
    }

}
