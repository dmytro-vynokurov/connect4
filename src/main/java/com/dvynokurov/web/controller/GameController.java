package com.dvynokurov.web.controller;

import com.dvynokurov.service.GameService;
import com.dvynokurov.web.dto.GameIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    GameService gameService;

    @RequestMapping(value = "/initialize", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public GameIdDto initializeGame(){
        return gameService.createNewGame();
    }
}
