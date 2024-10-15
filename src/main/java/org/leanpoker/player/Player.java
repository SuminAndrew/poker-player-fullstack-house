package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(JsonNode gameState) {
        return betStrategyCallHighestBet(gameState);
    }

    public static void showdown(JsonNode gameState) {
    }

    private static int betStrategyCallHighestBet(JsonNode gameState) {
        int defaultBet = 100;

        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = Utils.ownBet(gameState);
        int minimumRaise = gameState.get("minimum_raise").asInt();

        return highestBet - ownBet + minimumRaise;
    }
}
