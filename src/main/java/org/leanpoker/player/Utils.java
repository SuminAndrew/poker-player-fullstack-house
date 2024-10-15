package org.leanpoker.player;

import lombok.extern.slf4j.Slf4j;
import org.leanpoker.player.protocol.GameCard;
import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;
import org.leanpoker.player.protocol.Status;

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
        List<GameCard> ownCards = ownPlayer.getHoleCards();
        return ownCards.stream().map(Utils::toCard).toList();
    }

    private static Card toCard(GameCard card) {
        return new Card(CardEvaluator.evaluateCard(card.getRank()), card.getSuit().getSuit());
    }

    public static List<Card> communityCards(GameState gameState) {
        List<GameCard> cards = gameState.getCommunityCards();
        return cards.stream()
                .map(Utils::toCard)
                .toList();
    }

    public static boolean hasAPair(List<Card> cards) {
        return Objects.equals(cards.get(0).rank(), cards.get(1).rank());
    }

    public static boolean hasAHighCard(List<Card> cards) {
        return cards.stream().anyMatch(card -> card.rank() > 10);
    }

    public static boolean hasTwoHighCards(List<Card> cards) {
        return cards.stream().allMatch(card -> card.rank() > 10);
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

    public static List<GamePlayer> otherActivePlayers(GameState gameState) {
        return gameState.getPlayers().stream()
                .filter(player -> Status.ACTIVE.equals(player.getStatus()))
                .filter(player -> !Utils.isOwnPlayer(player))
                .toList();
    }

    public static int ownStack(GameState gameState) {
        GamePlayer ownPlayer = ownPlayer(gameState);
        return ownPlayer.getStack();
    }
}
