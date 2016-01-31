package com.dvynokurov.util.exceptions;

public class GridFullException extends RuntimeException {
    public GridFullException() {
        super("Grid is full");
    }
}
