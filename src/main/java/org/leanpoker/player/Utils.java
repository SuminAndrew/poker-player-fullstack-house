package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Slf4j
public class Utils {
    public static int getHighestBet(JsonNode request) {
        return request.get("current_buy_in").asInt();
//        JsonNode players = request.get("players");
//        int maxBet = StreamSupport.stream(players.spliterator(), false)
//                .filter(jsonNode -> !isOwnPlayer(jsonNode))
//                .map(jsonNode -> jsonNode.get("bet").asInt())
//                .max(Integer::compareTo)
//                .orElse(0);
//        return maxBet;
    }

    public static int ownBet(JsonNode gameState) {
        return ownPlayer(gameState).get("bet").asInt();
    }

    private static boolean isOwnPlayer(JsonNode player) {
        return Player.VERSION.equals(player.get("version").asText());
    }

    public static JsonNode ownPlayer(JsonNode gameState) {
        JsonNode players = gameState.get("players");
        return StreamSupport.stream(players.spliterator(), false)
                .filter(Utils::isOwnPlayer)
                .findAny()
                .orElseThrow();
    }

    public static List<Card> ownCards(JsonNode gameState) {
        JsonNode ownPlayer = ownPlayer(gameState);
        JsonNode ownCards = ownPlayer.get("hole_cards");
        return StreamSupport.stream(ownCards.spliterator(), false)
                .map(jsonNode -> new Card(CardEvaluator.evaluateCard(jsonNode.get("rank").asText()), jsonNode.get("suit").asText()))
                .toList();
    }

    public static boolean hasAPair(List<Card> cards) {
        return Objects.equals(cards.get(0).rank(), cards.get(1).rank());
    }

    public static boolean hasAHighCard(List<Card> cards) {
        return cards.stream().anyMatch(card -> card.rank() > 10);
    }

    public static boolean hasPossibleStraightFlash(List<Card> cards) {
        int rankDifference = Math.abs(cards.get(0).rank() - cards.get(1).rank());
        if (rankDifference > 1) {
            return false;
        }
        if (!Objects.equals(cards.get(0).suit(), cards.get(1).suit())) {
            return false;
        }
        if (cards.stream().anyMatch(card -> card.rank() <= 3)) {
            return false;
        }
        return true;
    }

    public static List<JsonNode> otherActivePlayers(JsonNode gameState) {
        JsonNode players = gameState.get("players");
        return StreamSupport.stream(players.spliterator(), false)
                .filter(jsonNode -> "active".equals(jsonNode.get("status").asText()))
                .filter(jsonNode -> !Utils.isOwnPlayer(jsonNode))
                .toList();
    }

    public static int ownStack(JsonNode gameState) {
        JsonNode ownPlayer = ownPlayer(gameState);
        return ownPlayer.get("stack").asInt();
    }
}
