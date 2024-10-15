package org.leanpoker.player.protocol;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Suit {
    CLUBS("clubs"),
    DIAMONDS("diamonds"),
    HEARTS("hearts"),
    SPADES("spades"),
    ;

    private final String suit;

    Suit(String suit) {
        this.suit = suit;
    }

    public static Suit fromString(String suit) {
        for (Suit s : Suit.values()) {
            if (s.suit.equals(suit)) {
                return s;
            }
        }
        return null;
    }
}
