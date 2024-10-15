package org.leanpoker.player;

import lombok.extern.slf4j.Slf4j;
import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;

import java.util.List;
import java.util.Objects;

@Slf4j
public class Utils {
    public static int getHighestBet(GameState gameState) {
        return gameState.getCurrentBuyIn();
//        JsonNode players = request.get("players");
//        int maxBet = StreamSupport.stream(players.spliterator(), false)
//                .filter(jsonNode -> !isOwnPlayer(jsonNode))
//                .map(jsonNode -> jsonNode.get("bet").asInt())
//                .max(Integer::compareTo)
//                .orElse(0);
//        return maxBet;
    }

    public static int ownBet(GameState gameState) {
        return ownPlayer(gameState).getBet();
    }

    private static boolean isOwnPlayer(GamePlayer player) {
        return Player.VERSION.equals(player.getVersion());
    }

    public static GamePlayer ownPlayer(GameState gameState) {
        return gameState.getPlayers().stream()
                .filter(Utils::isOwnPlayer)
                .findAny()
                .orElseThrow();
    }

    public static List<Card> ownCards(GameState gameState) {
        GamePlayer ownPlayer = ownPlayer(gameState);
        return ownPlayer.getHoleCards().stream()
                .map(card -> new Card(CardEvaluator.evaluateCard(card.getRank()), card.getSuit().getSuit()))
                .toList();
    }

    public static boolean hasAPair(List<Card> cards) {
        return Objects.equals(cards.get(0).rank(), cards.get(1).rank());
    }

    public static boolean hasAHighCard(List<Card> cards) {
        return cards.stream().anyMatch(card -> card.rank() > 10);
    }
}
