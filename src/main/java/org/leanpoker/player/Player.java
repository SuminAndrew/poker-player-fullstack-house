package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(JsonNode gameState) {
        List<Card> ownCards = Utils.ownCards(gameState);

        if (Utils.hasAPair(ownCards) || Utils.hasAHighCard(ownCards)) {
            return betStrategyCallHighestBet(gameState);
        } else {
            return 0;
        }
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
