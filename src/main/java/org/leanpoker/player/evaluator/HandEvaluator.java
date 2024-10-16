package org.leanpoker.player.evaluator;

import org.apache.commons.numbers.combinatorics.Combinations;
import org.leanpoker.player.protocol.GameCard;

import java.util.List;

import java.util.ArrayList;

public class HandEvaluator {
    public static int evaluateHand(List<GameCard> holeCards, List<GameCard> communityCards) {
        Card[] cards = Hand.fromCards(holeCards, communityCards);
        try {
            if (cards.length == 5) {
                return Hand.evaluate(cards);
            } else if (cards.length == 6 || cards.length == 7) {
                return getAllCombinations(cards, 5).stream().mapToInt(Hand::evaluate).min().orElse(-1);
            }

            return - 1;
        } catch (Exception e) {
            return -1;
        }
    }

    static List<Card[]> getAllCombinations(Card[] e, int k) {
        List<Card[]> combinations = new ArrayList<>();

        Combinations.of(e.length, k).iterator().forEachRemaining(combination -> {
            Card[] cardCombination = new Card[combination.length];
            for (int z = 0 ; z < combination.length; z++) {
                cardCombination[z] = e[combination[z]];
            }
            combinations.add(cardCombination);
        });

        return combinations;
    }
}
