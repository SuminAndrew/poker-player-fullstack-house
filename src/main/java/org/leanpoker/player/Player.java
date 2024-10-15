package org.leanpoker.player;

import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;

import java.util.List;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(GameState gameState) {
        try {
            List<GamePlayer> otherActivePlayers = Utils.otherActivePlayers(gameState);
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

    public static void showdown(GameState gameState) {
    }

    private static int minimumRaise(GameState gameState) {
        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = Utils.ownBet(gameState);
        int minimumRaise = gameState.getMinimumRaise();
        return highestBet - ownBet + minimumRaise;
    }

    private static int call(GameState gameState) {
        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = Utils.ownBet(gameState);
        return highestBet - ownBet;
    }
}
