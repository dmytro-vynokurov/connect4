package com.dvynokurov.model;

import org.springframework.util.Assert;

public class Cell {

    private int index;
    private Owner owner;

    public Cell(int index) {
        Assert.isTrue(index >= 0, "Cell index should not be negative");
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
