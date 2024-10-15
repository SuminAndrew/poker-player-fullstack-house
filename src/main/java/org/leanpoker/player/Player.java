package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Player {

    static final String VERSION = "Default Java folding player";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static int betRequest(JsonNode request) {
        GameState gameState = mapper.treeToValue(request, GameState.class);
        return 10;
    }

    public static void showdown(JsonNode game) {
    }
}
