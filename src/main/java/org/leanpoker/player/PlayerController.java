package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;
import org.leanpoker.player.protocol.GameState;

import java.util.Map;

@Slf4j
@Controller()
public class PlayerController {

    ObjectMapper mapper = new ObjectMapper();

    @Get(produces = MediaType.TEXT_PLAIN)
    public String doGet() {
        return "Java player is running";
    }

    @Post(produces = MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String doPost(@Body Map<String, String> body) throws JsonProcessingException {
        try {
            log.info("game body:\n{}", mapper.writeValueAsString(body));
        } catch (Throwable t) {
            log.error("error:", t);
        }

        String action = body.get("action");
        String gameState = body.get("game_state");
        if ("bet_request".equals(action)) {
            GameState state = mapper.readValue(gameState, GameState.class);
            return String.valueOf(Player.betRequest(state));
        }
        if ("showdown".equals(action)) {
            GameState state = mapper.readValue(gameState, GameState.class);
            Player.showdown(state);
        }
        if ("version".equals(action)) {
            return Player.VERSION;
        }
        return "";
    }

}
