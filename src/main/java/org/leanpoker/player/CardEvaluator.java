package org.leanpoker.player;

public class CardEvaluator {
    public static int evaluateCard(String rank) {
        return switch (rank) {
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            case "A" -> 14;
            default -> Integer.parseInt(rank);
        };
    }
}
