package com.dvynokurov.service;

import com.dvynokurov.model.Game;
import com.dvynokurov.repository.GameRepository;
import com.dvynokurov.web.dto.GameIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    @Value("${game.grid.width}")
    private int gridWidth;

    @Value("${game.grid.height}")
    private int gridHeight;

    @Autowired
    GameRepository gameRepository;

    public GameIdDto createNewGame(){
        UUID uuid = UUID.randomUUID();
        Game game = new Game(uuid, gridWidth, gridHeight);
        gameRepository.save(game);
        return new GameIdDto(uuid.toString());
    }

}
