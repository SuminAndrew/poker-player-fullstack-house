package org.leanpoker.player;

import org.leanpoker.player.protocol.GameState;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(GameState state) {
        return 10;
    }

    public static void showdown(GameState state) {
    }
}
