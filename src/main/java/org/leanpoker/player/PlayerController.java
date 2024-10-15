package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.leanpoker.player.protocol.Action;
import org.leanpoker.player.protocol.GameState;

import java.util.Map;

@Controller()
public class PlayerController {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Get(produces = MediaType.TEXT_PLAIN)
    public String doGet() {
        return "Java player is running";
    }

    @Post(produces = MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String doPost(@Body Map<String, String> body) throws JsonProcessingException {
        Action action = Action.fromString(body.get("action"));
        if (Action.BET_REQUEST.equals(action)) {
            GameState state = mapper.readValue(body.get("game_state"), GameState.class);
            return String.valueOf(Player.betRequest(state));
        }
        if (Action.SHOWDOWN.equals(action)) {
            GameState state = mapper.readValue(body.get("game_state"), GameState.class);
            Player.showdown(state);
        }
        if (Action.VERSION.equals(action)) {
            return Player.VERSION;
        }
        return "";
    }

}
