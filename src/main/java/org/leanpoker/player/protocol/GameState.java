package org.leanpoker.player.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/*
{
  "players":[
    {
      "name":"Player 1",
      "stack":1000,
      "status":"active",
      "bet":0,
      "hole_cards":[],
      "version":"Version name 1",
      "id":0
    },
    {
      "name":"Player 2",
      "stack":1000,
      "status":"active",
      "bet":0,
      "hole_cards":[],
      "version":"Version name 2",
      "id":1
    }
  ],
  "tournament_id":"550d1d68cd7bd10003000003",
  "game_id":"550da1cb2d909006e90004b1",
  "round":0,
  "bet_index":0,
  "small_blind":10,
  "orbits":0,
  "dealer":0,
  "community_cards":[],
  "current_buy_in":0,
  "pot":0
}
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameState {

    /**
     * Id of the current tournament
     */
    @JsonProperty("tournament_id")
    private String tournamentId;

    /**
     * Id of the current sit'n'go game. You can use this to link a
     * sequence of game states together for logging purposes, or to
     * make sure that the same strategy is played for an entire game
     */
    @JsonProperty("game_id")
    private String gameId;

    /**
     * Index of the current round within a sit'n'go
     */
    @JsonProperty("round")
    private int round;

    /**
     * Index of the betting opportunity within a round
     */
    @JsonProperty("bet_index")
    private int betIndex;

    @JsonProperty("big_blind")
    private int bigBlind;

    @JsonProperty("small_blind")
    private int smallBlind;

    /**
     * Number of orbits completed. (The number of times the dealer
     * button returned to the same player.)
     */
    private int orbits;

    /**
     * The index of the player on the dealer button in this round
     * The first player is (dealer+1)%(players.length)
     */
    private int dealer;

    @JsonProperty("current_buy_in")
    private int currentBuyIn;

    /**
     * The size of the pot (sum of the player bets)
     */
    private int pot;

    @JsonProperty("community_cards")
    private List<Card> communityCards;

    private List<GamePlayer> players;

    @JsonProperty("minimum_raise")
    private int minimumRaise;

    @JsonProperty("in_action")
    private int inAction;
}
