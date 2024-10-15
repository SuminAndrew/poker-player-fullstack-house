package org.leanpoker.player.protocol;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Card {

    private String rank;

    private Suit suit;
}
