package org.leanpoker.player;

import org.leanpoker.player.evaluator.HandEvaluator;
import org.leanpoker.player.protocol.GameCard;
import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;

import java.util.List;

import static org.leanpoker.player.Utils.bigBlind;
import static org.leanpoker.player.Utils.hasAHighCard;
import static org.leanpoker.player.Utils.hasAHighPair;
import static org.leanpoker.player.Utils.hasAPairOrMore;
import static org.leanpoker.player.Utils.hasAnAce;
import static org.leanpoker.player.Utils.hasPossibleStraightFlash;
import static org.leanpoker.player.Utils.hasSameSuit;
import static org.leanpoker.player.Utils.hasTwoHighCards;
import static org.leanpoker.player.Utils.hasTwoSuperHighCards;
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

            // 3 to 5 community cards
            List<GameCard> communityCards = gameState.getCommunityCards();
            if ((communityCards.size() >= 3) && (communityCards.size() <= 5)) {
                int handRating = HandEvaluator.evaluateHand(Utils.ownCards(gameState), gameState.getCommunityCards());
                if (hasAPairOrMore(communityCards)) {
                    handRating += 500;
                }
                if (handRating >= 0 && handRating < 1500) {
                    return minimumRaise(gameState);
                } else if (handRating < 3000) {
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
            if (otherActivePlayers.size() == 1 &&
                    ((hasTwoSuperHighCards(ownCards) || hasAHighPair(ownCards)))) {
                return allIn(gameState);
            }

            if (hasTwoSuperHighCards(ownCards) || hasAHighPair(ownCards)) {
                return minimumRaise(gameState);
            } else if (hasTwoHighCards(ownCards) && hasAnAce(ownCards) && hasSameSuit(ownCards)) {
                return call(gameState);
            } else if (hasPossibleStraightFlash(ownCards) ||
                    hasAPairOrMore(ownCards) ||
                    hasTwoHighCards(ownCards)) {
                if (otherActivePlayers.size() == 1) {
                    return call(gameState);
                } else {
                    return smallCallOrFold(gameState);
                }
            } else {
                if (otherActivePlayers.size() == 1) {
                    if (hasAHighCard(ownCards) || hasAPairOrMore(ownCards)) {
                        return allIn(gameState);
                    }
                }
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return minimumRaise(gameState);
    }

    public static void showdown(GameState gameState) {
    }

    private static int allIn(GameState gameState) {
        return ownStack(gameState);
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

    private static int smallCallOrFold(GameState gameState) {
        int amountToCall = call(gameState);
        if (amountToCall < ownStack(gameState) * 0.01 &&
                ownBet(gameState) <= bigBlind(gameState)) {
            return amountToCall;
        } else {
            return 0;
        }
    }
}
