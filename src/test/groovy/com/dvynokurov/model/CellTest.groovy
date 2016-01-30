package com.dvynokurov.model

import spock.lang.Specification

class CellTest extends Specification {
    def "cell gets created with empty owner"(){
        when:
        Cell cell = new Cell(0)

        then:
        cell.getOwner() == Owner.EMPTY
    }

    def "cell should support positive indexes"(){
        when:
        new Cell(index)

        then:
        noExceptionThrown()

        where:
        index << [1, 5, 10, 20, 256]
    }

    def "cell should not support negative indexes"(){
        when:
        new Cell(index)

        then:
        thrown(Exception)

        where:
        index << [-1, -2, -5, -10, -40, -200]
    }
}
