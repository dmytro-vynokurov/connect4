package com.dvynokurov.service;

import com.dvynokurov.model.Grid;
import com.dvynokurov.util.exceptions.GridFullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AIPlayer {

    @Autowired
    GridFillingService gridFillingService;

    public int getComputerMove(Grid grid) {
        List<Integer> availableIndexes = getColumnNumbers(grid);
        Random random = new Random(); //todo: ideally should be ThreadLocal

        while(availableIndexes.size()>0) {
            Integer index = getRandomIndexFromList(availableIndexes, random);
            if (gridFillingService.columnHasEmptyCells(grid, index)) return index;
            else availableIndexes.remove(index);
        }
        throw new GridFullException();
    }

    private Integer getRandomIndexFromList(List<Integer> availableIndexes, Random random) {
        return availableIndexes.get(random.nextInt(availableIndexes.size()));
    }

    private List<Integer> getColumnNumbers(Grid grid) {
        return IntStream.range(0, grid.getColumns().size()).mapToObj(x -> x).collect(Collectors.toList());
    }
}
