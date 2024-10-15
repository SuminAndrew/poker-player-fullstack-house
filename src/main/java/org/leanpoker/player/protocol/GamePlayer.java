package org.leanpoker.player.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GamePlayer {
    private String name;

    private int stack;

    private Status status;

    private int bet;

    @JsonProperty("hole_cards")
    private List<Card> holeCards;

    private String version;

    private int id;
}
