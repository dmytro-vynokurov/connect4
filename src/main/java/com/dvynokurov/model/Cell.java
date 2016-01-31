package com.dvynokurov.model;

public class Cell {

    private Player owner;

    public Cell() {
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return owner == cell.owner;

    }

    @Override
    public int hashCode() {
        return owner != null ? owner.hashCode() : 0;
    }
}
