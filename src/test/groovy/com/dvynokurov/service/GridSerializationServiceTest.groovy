package com.dvynokurov.service

import com.dvynokurov.model.Cell
import com.dvynokurov.model.Column
import com.dvynokurov.model.Grid
import com.dvynokurov.model.Player
import spock.lang.Specification

class GridSerializationServiceTest extends Specification {

    def "grid should not change after encode-decode cycle"(){
        given:
        Cell c0 = new Cell();
        Cell c1 = new Cell();
        c1.setOwner(Player.FIRST);
        Cell c2 = new Cell();
        c2.setOwner(Player.SECOND);
        Column col1 = new Column(Arrays.asList(c1,c2,c1,c2,c0));
        Column col2 = new Column(Arrays.asList(c1,c1,c1,c2,c2));
        Grid grid = new Grid(Arrays.asList(col1, col2));
        def gridSerializationService = new GridSerializationService()
        gridSerializationService.gridGenerationService = new GridGenerationService()

        when:
        byte[] bytes = gridSerializationService.encode(grid)
        def decodedGrid = gridSerializationService.decode(bytes)

        then:
        grid == decodedGrid

    }

}
