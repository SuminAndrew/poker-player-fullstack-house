package org.leanpoker.player.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GamePlayer {
    private String name;

    private int stack;

    private Status status;

    private int bet;

    @JsonProperty("hole_cards")
    private List<Card> holeCards;

    private String version;

    private int id;

    @JsonProperty("time_used")
    private int timeUsed;

    @JsonProperty("amount_won")
    private int amountWon;
}
