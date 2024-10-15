package org.leanpoker.player;

import org.leanpoker.player.protocol.GameState;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(GameState gameState) {
        return betStrategyCallHighestBet(gameState);
    }

    public static void showdown(GameState gameState) {

    }

    private static int betStrategyCallHighestBet(GameState gameState) {
        int defaultBet = 100;

        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = Utils.ownBet(gameState);
        int minimumRaise = gameState.getMinimumRaise();

        return highestBet - ownBet + minimumRaise;
    }
}
