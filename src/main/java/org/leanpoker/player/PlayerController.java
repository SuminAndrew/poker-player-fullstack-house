package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Named;
import org.leanpoker.player.protocol.GameState;

import java.util.Map;

@Controller()
public class PlayerController {

    private final ObjectMapper objectMapper;
    ObjectMapper mapper = new ObjectMapper();

    public PlayerController(@Named("json") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String doGet() {
        return "Java player is running";
    }

    @Post(produces = MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String doPost(@Body Map<String, String> body) throws JsonProcessingException {
        String action = body.get("action");
        String gameState = body.get("game_state");
        GameState state = objectMapper.readValue(gameState, GameState.class);
        if ("bet_request".equals(action)) {
            return String.valueOf(Player.betRequest(state));
        }
        if ("showdown".equals(action)) {
            Player.showdown(state);
        }
        if ("version".equals(action)) {
            return Player.VERSION;
        }
        return "";
    }

}
