package com.dvynokurov.model

import spock.lang.Specification

class CellTest extends Specification {
    def "cell gets created with empty owner"(){
        when:
        Cell cell = new Cell()

        then:
        cell.getOwner() == Owner.EMPTY
    }
}
