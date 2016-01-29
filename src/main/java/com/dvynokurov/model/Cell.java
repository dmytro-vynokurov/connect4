package com.dvynokurov.model;

public class Cell {

    private int index;
    private Owner owner;

    public Cell(int index) {
        this.index = index;
        this.owner = Owner.EMPTY;
    }

    public int getIndex() {
        return index;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
