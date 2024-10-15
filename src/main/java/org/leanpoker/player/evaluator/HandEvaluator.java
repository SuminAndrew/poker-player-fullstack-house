package org.leanpoker.player.evaluator;

import org.leanpoker.player.protocol.GameCard;

import java.util.List;

public class HandEvaluator {
    public static int evaluateHand(List<GameCard> holeCards, List<GameCard> communityCards) {
        Card[] cards = Hand.fomCards(holeCards, communityCards);
        return Hand.evaluate(cards);
    }
}
