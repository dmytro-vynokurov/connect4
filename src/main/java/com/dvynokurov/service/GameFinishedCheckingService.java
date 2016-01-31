package com.dvynokurov.service;

import com.dvynokurov.model.Grid;
import com.dvynokurov.model.Player;
import org.springframework.stereotype.Service;

@Service
public class GameFinishedCheckingService {

    public boolean checkFinished(Grid grid, Player player) {
        return false;
    }

}
