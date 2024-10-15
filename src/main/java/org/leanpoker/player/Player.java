package org.leanpoker.player;

import org.leanpoker.player.evaluator.HandEvaluator;
import org.leanpoker.player.protocol.GameCard;
import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;

import java.util.List;

import static org.leanpoker.player.Utils.hasPossibleStraightFlash;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(GameState gameState) {
        try {
            List<GamePlayer> otherActivePlayers = Utils.otherActivePlayers(gameState);
            if (otherActivePlayers.size() > 1 && Utils.ownStack(gameState) <= 50 && Utils.ownBet(gameState) < 50) {
                return 0;
            }

            List<GameCard> communityCards = gameState.getCommunityCards();
            if (communityCards.size() == 3) {
                int handRating = HandEvaluator.evaluateHand(Utils.ownCards(gameState), gameState.getCommunityCards());
                System.out.printf("Evaluate: ownCard=%s, communityCards=%s, rating=%d%n", Utils.ownCards(gameState), communityCards, handRating);
                if (handRating >= 0 && handRating < 2000) {
                    return minimumRaise(gameState);
                } else if (handRating < 4000) {
                    return call(gameState);
                } else {
                    return 0;
                }
            }
            if (communityCards.size() > 3) {
                return minimumRaise(gameState);
            }

            List<GameCard> ownCards = Utils.ownCards(gameState);
            if (Utils.hasTwoHighCards(ownCards)) {
                return minimumRaise(gameState);
            } else if (hasPossibleStraightFlash(ownCards) ||
                    (Utils.hasAHighCard(ownCards) && Utils.hasSameSuit(ownCards)) ||
                    (Utils.hasAPair(ownCards) && !Utils.hasAHighCard(ownCards))) {
                return call(gameState);
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
