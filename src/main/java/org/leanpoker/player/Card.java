package org.leanpoker.player;

public record Card(
        int rank,
        /**
         * hearts
         * spades
         * clubs
         * diamonds
         */
        String suit
) {
}
