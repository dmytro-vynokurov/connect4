package com.dvynokurov.util.exceptions;

public class ColumnFullException extends RuntimeException {
    public ColumnFullException() {
        super("Cannot add disk to column. Column is full.");
    }
}
