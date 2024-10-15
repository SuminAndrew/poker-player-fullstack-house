package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(JsonNode gameState) {
        try {
            List<JsonNode> otherActivePlayers = Utils.otherActivePlayers(gameState);
            if (otherActivePlayers.size() > 1 && Utils.ownStack(gameState) <= 50) {
                return 0;
            }

            List<Card> ownCards = Utils.ownCards(gameState);
            if (Utils.hasAPair(ownCards) || Utils.hasAHighCard(ownCards) ||
                    Utils.hasPossibleStraightFlash(ownCards)) {
                return minimumRaise(gameState);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return minimumRaise(gameState);
    }

    public static void showdown(JsonNode gameState) {
    }

    private static int minimumRaise(JsonNode gameState) {
        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = Utils.ownBet(gameState);
        int minimumRaise = gameState.get("minimum_raise").asInt();

        return highestBet - ownBet + minimumRaise;
    }

    private static int call(JsonNode gameState) {
        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = Utils.ownBet(gameState);
        return highestBet - ownBet;
    }
}
