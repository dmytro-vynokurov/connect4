package com.dvynokurov.service;

import com.dvynokurov.model.Grid;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AIPlayer {

    public int getComputerMove(Grid grid) {
        return new Random().nextInt(grid.getColumns().size());
    }
}
