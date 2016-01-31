package com.dvynokurov.web.controller;

import com.dvynokurov.model.Game;
import com.dvynokurov.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    GameService gameService;

    @RequestMapping(value = "/initialize", method = RequestMethod.POST)
    public Game initializeGame() {
        return gameService.createNewGame();
    }

    @RequestMapping(value = "/{gameId}/{columnNumber}", method = RequestMethod.POST)
    public Game performPlayerMove(
            @PathVariable("gameId") String gameId,
            @PathVariable("columnNumber") int columnNumber
    ) {
        UUID id = UUID.fromString(gameId);
        return gameService.performPlayerMove(id, columnNumber);
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public Game getGameStatus(@PathVariable("gameId") UUID gameId) {
        return gameService.getGame(gameId);
    }
}
