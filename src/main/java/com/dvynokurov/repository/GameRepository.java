package com.dvynokurov.repository;

import com.dvynokurov.model.Game;
import com.dvynokurov.model.GameStatus;
import com.dvynokurov.model.Grid;
import com.dvynokurov.service.GridSerializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameRepository {

    public static final byte[] GRID_HASH_KEY = "grid".getBytes();
    public static final byte[] STATUS_HASH_KEY = "status".getBytes();
    public static final byte[] FIRST_PLAYER_LAST_MOVE = "p1lm".getBytes();
    public static final byte[] SECOND_PLAYER_LAST_MOVE = "p2lm".getBytes();

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    GridSerializationService gridSerializationService;

    public void save(Game game) {
        Map<byte[], byte[]> gameAsMap = new HashMap<>();
        gameAsMap.put(GRID_HASH_KEY, gridSerializationService.encode(game.getGrid()));
        gameAsMap.put(STATUS_HASH_KEY, game.getGameStatus().toString().getBytes());
        if(game.getFirstPlayerLastMove()!=null) gameAsMap.put(FIRST_PLAYER_LAST_MOVE, game.getFirstPlayerLastMove().toString().getBytes());
        if(game.getSecondPlayerLastMove()!=null) gameAsMap.put(SECOND_PLAYER_LAST_MOVE, game.getSecondPlayerLastMove().toString().getBytes());
        redisTemplate.executePipelined((RedisConnection connection)->{
            connection.hMSet(game.getId().toString().getBytes(), gameAsMap);
            return null;
        });
    }

    public Game findOneById(UUID gameId) {
        return redisTemplate.execute((RedisConnection connection) -> {
            Map<byte[], byte[]> gameAsMap = connection.hGetAll(gameId.toString().getBytes());

            Grid grid = gridSerializationService.decode(gameAsMap.get(GRID_HASH_KEY));
            GameStatus gameStatus = GameStatus.valueOf(new String(gameAsMap.get(STATUS_HASH_KEY)));

            Game game = new Game(gameId, grid, gameStatus);

            byte[] firstPlayerLastMove = gameAsMap.get(FIRST_PLAYER_LAST_MOVE);
            if(firstPlayerLastMove!=null) game.setFirstPlayerLastMove(Integer.parseInt(new String(firstPlayerLastMove)));

            byte[] secondPlayerLastMove = gameAsMap.get(SECOND_PLAYER_LAST_MOVE);
            if(secondPlayerLastMove!=null)game.setSecondPlayerLastMove(Integer.parseInt(new String(secondPlayerLastMove)));

            return game;
        });
    }
}
