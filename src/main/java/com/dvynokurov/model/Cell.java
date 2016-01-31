package com.dvynokurov.model;

public class Cell {

    private Owner owner;

    public Cell() {
        this.owner = Owner.EMPTY;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
