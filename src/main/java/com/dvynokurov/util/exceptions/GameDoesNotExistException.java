package com.dvynokurov.util.exceptions;

public class GameDoesNotExistException extends RuntimeException {
    public GameDoesNotExistException() {
        super("Game does not exist");
    }
}
