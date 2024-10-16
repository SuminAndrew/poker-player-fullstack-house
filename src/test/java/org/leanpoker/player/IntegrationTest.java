package org.leanpoker.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.leanpoker.player.protocol.Action;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class IntegrationTest {

    private final PlayerController controller = new PlayerController();

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Request> requests = mapper.readValue(read("1.json"), new TypeReference<List<Request>>(){});
        for (Request request : requests) {
            String resp = controller.doPost(request.toBody());
            if (Action.BET_REQUEST.equals(Action.fromString(request.action))) {
                assertNotEquals("", resp);
                assertTrue(NumberUtils.isNumber(resp));
            } else if (Action.VERSION.equals(Action.fromString(request.action))) {
                assertEquals(Player.VERSION, resp);
            } else if (Action.SHOWDOWN.equals(Action.fromString(request.action))) {
                assertEquals("", resp);
            } else {
                fail("Unknown action: " + request.action);
            }
        }
    }

    private String read(String fileName) {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    private static class Request {

        @JsonProperty("action")
        private String action;

        @JsonProperty("game_state")
        private String gameState;

        public Map<String, String> toBody() {
            Map<String, String> body = new HashMap<>();
            body.put("action", action);
            if (StringUtils.isNotBlank(gameState)) {
                body.put("game_state", gameState);
            }
            return body;
        }
    }
}
