package org.leanpoker.player;

import org.leanpoker.player.evaluator.HandEvaluator;
import org.leanpoker.player.protocol.GameCard;
import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;

import java.util.List;

import static org.leanpoker.player.Utils.hasAHighCard;
import static org.leanpoker.player.Utils.hasAPair;
import static org.leanpoker.player.Utils.hasHighPair;
import static org.leanpoker.player.Utils.hasPossibleStraightFlash;
import static org.leanpoker.player.Utils.hasSameSuit;
import static org.leanpoker.player.Utils.hasTwoHighCards;
import static org.leanpoker.player.Utils.hasTwoSuperHighCards;
import static org.leanpoker.player.Utils.isOurBetLow;
import static org.leanpoker.player.Utils.ownBet;
import static org.leanpoker.player.Utils.ownStack;

public class Player {

    static final String VERSION = "Java Bot v1";

    public static int betRequest(GameState gameState) {
        try {
            // We are low on money
            List<GamePlayer> otherActivePlayers = Utils.otherActivePlayers(gameState);
            if (otherActivePlayers.size() > 1 && ownStack(gameState) <= 50 && ownBet(gameState) < 50) {
                return 0;
            }

            // 3 or 4 community cards
            List<GameCard> communityCards = gameState.getCommunityCards();
            if ((communityCards.size() >= 3) && (communityCards.size() <= 5)) {
                int handRating = HandEvaluator.evaluateHand(Utils.ownCards(gameState), gameState.getCommunityCards());
                if (handRating >= 0 && handRating < 2000) {
                    return minimumRaise(gameState);
                } else if (handRating < 4000) {
                    return call(gameState);
                } else {
                    return 0;
                }
            } else if (communityCards.size() > 5) {
                // ???
                return minimumRaise(gameState);
            }

            // no community cards
            List<GameCard> ownCards = Utils.ownCards(gameState);
            if (hasTwoSuperHighCards(ownCards) || hasHighPair(ownCards)) {
                return minimumRaise(gameState);
            } else if (hasTwoHighCards(ownCards)) {
                if (isOurBetLow(gameState)) {
                    return minimumRaise(gameState);
                } else {
                    return 0;
                }
            } else if (hasPossibleStraightFlash(ownCards) ||
                    (hasAHighCard(ownCards) && hasSameSuit(ownCards)) ||
                    (hasAPair(ownCards) && !hasAHighCard(ownCards))) {
                if (isOurBetLow(gameState)) {
                    return call(gameState);
                } else {
                    return 0;
                }
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
        int ownBet = ownBet(gameState);
        int minimumRaise = gameState.getMinimumRaise();
        return highestBet - ownBet + minimumRaise;
    }

    private static int call(GameState gameState) {
        int highestBet = Utils.getHighestBet(gameState);
        int ownBet = ownBet(gameState);
        return highestBet - ownBet;
    }
}
