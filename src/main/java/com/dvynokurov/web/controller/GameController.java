package com.dvynokurov.web.controller;

import com.dvynokurov.model.Game;
import com.dvynokurov.service.GameService;
import com.dvynokurov.util.exceptions.ColumnFullException;
import com.dvynokurov.util.exceptions.GameDoesNotExistException;
import com.dvynokurov.util.exceptions.GridFullException;
import com.dvynokurov.web.dto.ExceptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable("gameId") UUID gameId,
            @PathVariable("columnNumber") int columnNumber
    ) {
        return gameService.performPlayerMove(gameId, columnNumber);
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public Game getGameStatus(@PathVariable("gameId") UUID gameId) {
        return gameService.getGame(gameId);
    }

    @ExceptionHandler(GridFullException.class)
    @ResponseStatus(HttpStatus.OK)
    public ExceptionDto handleGridFullException(){
        return new ExceptionDto("Grid is full");
    }

    @ExceptionHandler(value = ColumnFullException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ExceptionDto handleColumnFullException() {
        return new ExceptionDto("Column is full");
    }

    @ExceptionHandler(value = GameDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionDto handleGameDoesNotExistException() {
        return new ExceptionDto("Game does not exist");
    }
}
