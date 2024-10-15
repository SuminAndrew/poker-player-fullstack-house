package org.leanpoker.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.leanpoker.player.protocol.GameState;

public class Player {

    static final String VERSION = "Default Java folding player";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static int betRequest(GameState state) {
        return 10;
    }

    public static void showdown(GameState state) {
    }
}
