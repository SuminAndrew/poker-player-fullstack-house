package org.leanpoker.player.evaluator;

import com.fasterxml.jackson.databind.JsonNode;

public class HandEvaluator {
    public static int evaluateHand(JsonNode holeCards, JsonNode communityCards) {
        Card[] cards = Hand.fromJson(holeCards, communityCards);
        return Hand.evaluate(cards);
    }
}
