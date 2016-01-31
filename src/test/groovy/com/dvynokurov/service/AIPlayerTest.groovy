package com.dvynokurov.service

import com.dvynokurov.model.Cell
import com.dvynokurov.model.Column
import com.dvynokurov.model.Grid
import com.dvynokurov.model.Player
import com.dvynokurov.util.exceptions.GridFullException
import spock.lang.Specification

import static java.util.Collections.singletonList

class AIPlayerTest extends Specification {
    def "getComputerMove should produce any number in case all columns in grid have empty cells"(){
        given:
        def cell = new Cell()
        def column = new Column(singletonList(cell))
        def grid = new Grid(singletonList(column))
        def aiPlayer = new AIPlayer()
        aiPlayer.gridFillingService = Mock(GridFillingService)

        when:
        aiPlayer.gridFillingService.columnHasEmptyCells(grid, 0) >> true
        def computerMove = aiPlayer.getComputerMove(grid)

        then:
        computerMove==0
    }

    def "getComputerMove should fail in case all columns in grid do not have empty cells"(){
        given:
        def cell = new Cell()
        cell.setOwner(Player.FIRST)
        def column = new Column(singletonList(cell))
        def grid = new Grid(singletonList(column))
        def aiPlayer = new AIPlayer()
        aiPlayer.gridFillingService = Mock(GridFillingService)

        when:
        aiPlayer.gridFillingService.columnHasEmptyCells(grid, 0) >> false
        def computerMove = aiPlayer.getComputerMove(grid)

        then:
        thrown(GridFullException)
    }
}
