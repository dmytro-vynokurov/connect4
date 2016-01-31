package com.dvynokurov.service

import com.dvynokurov.model.Cell
import com.dvynokurov.model.Column
import com.dvynokurov.model.Grid
import com.dvynokurov.model.Player
import spock.lang.Specification

import static java.util.Collections.singletonList

class GameFinishedCheckingServiceTest extends Specification {

    def "game that is not finished should be treated as not finished"(){
        given:
        def c0 = new Cell()
        def c1 = new Cell()
        c1.setOwner(Player.FIRST)
        def c2 = new Cell()
        c2.setOwner(Player.SECOND)
        def column = new Column([c1, c2, c1, c1, c2, c0, c0, c0])
        def grid = new Grid(singletonList(column))

        def checkingService = new GameFinishedCheckingService()

        when:
        def finished = checkingService.checkFinished(grid, Player.FIRST)

        then:
        !finished
    }

    def "game that finished by vertical line should be treated finished"(){
        given:
        def c0 = new Cell()
        def c1 = new Cell()
        c1.setOwner(Player.FIRST)
        def c2 = new Cell()
        c2.setOwner(Player.SECOND)
        def col1 = new Column([c1, c1, c1, c1, c0, c0])
        def col2 = new Column([c2, c2, c2, c0, c0, c0])
        def grid = new Grid([col1, col2])

        def checkingService = new GameFinishedCheckingService()

        when:
        def finished = checkingService.checkFinished(grid, Player.FIRST)

        then:
        finished
    }

    def "game that finished by horizontal line should be treated finished"(){
        given:
        def c0 = new Cell()
        def c1 = new Cell()
        c1.setOwner(Player.FIRST)
        def c2 = new Cell()
        c2.setOwner(Player.SECOND)
        def col1 = new Column([c1, c1, c1, c2, c2, c0])
        def col2 = new Column([c2, c2, c1, c2, c0, c0])
        def col3 = new Column([c1, c1, c1, c2, c0, c0])
        def col4 = new Column([c2, c2, c1, c0, c0, c0])
        def grid = new Grid([col1, col2, col3, col4])

        def checkingService = new GameFinishedCheckingService()

        when:
        def finished = checkingService.checkFinished(grid, Player.FIRST)

        then:
        finished
    }

    def "game that finished by diagonal line from left upper to right lower side should be treated finished"(){
        given:
        def c0 = new Cell()
        def c1 = new Cell()
        c1.setOwner(Player.FIRST)
        def c2 = new Cell()
        c2.setOwner(Player.SECOND)
        def col1 = new Column([c1, c2, c1, c2, c1, c0])
        def col2 = new Column([c2, c2, c1, c1, c0, c0])
        def col3 = new Column([c1, c1, c1, c2, c0, c0])
        def col4 = new Column([c2, c1, c2, c0, c0, c0])
        def grid = new Grid([col1, col2, col3, col4])

        def checkingService = new GameFinishedCheckingService()

        when:
        def finished = checkingService.checkFinished(grid, Player.FIRST)

        then:
        finished
    }

    def "game that finished by diagonal line from right upper to left lower side should be treated finished"(){
        given:
        def c0 = new Cell()
        def c1 = new Cell()
        c1.setOwner(Player.FIRST)
        def c2 = new Cell()
        c2.setOwner(Player.SECOND)
        def col1 = new Column([c2, c1, c2, c0, c0, c0])
        def col2 = new Column([c1, c1, c1, c2, c0, c0])
        def col3 = new Column([c2, c2, c1, c1, c0, c0])
        def col4 = new Column([c1, c2, c1, c2, c1, c0])
        def grid = new Grid([col1, col2, col3, col4])

        def checkingService = new GameFinishedCheckingService()

        when:
        def finished = checkingService.checkFinished(grid, Player.FIRST)

        then:
        finished
    }
}
