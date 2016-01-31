package com.dvynokurov.service

import spock.lang.Specification

class GridGenerationServiceTest extends Specification {
    def "generateGrid method should produce grid with positive size parameters"() {
        when:
        def generationService = new GridGenerationService()
        def grid = generationService.generateGrid(width, height)

        then:
        grid.columns.size() == width
        grid.columns.get(0).cells.size() == height

        where:
        width | height
        1     | 1
        1     | 2
        1     | 5
        1     | 10
        1     | 50
        2     | 1
        4     | 1
        5     | 1
        10    | 1
        4     | 6
        6     | 7
    }

    def "generateGrid should produce IllegalArgumentException when grid size parameters are not positive"() {
        when:
        def generationService = new GridGenerationService()
        def grid = generationService.generateGrid(width, height)

        then:
        thrown(IllegalArgumentException)

        where:
        width | height
        0     | 0
        0     | 1
        0     | 3
        0     | 5
        1     | 0
        3     | 0
        5     | 0
        -1    | 0
        0     | -1
        -1    | -1
        -2    | -2
        -3    | -5
        -6    | -7
    }
}
