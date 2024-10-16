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

    public static List<GameCard> ownCards(GameState gameState) {
        GamePlayer ownPlayer = ownPlayer(gameState);
        return ownPlayer.getHoleCards();
    }

    public static boolean hasALowPair(List<GameCard> cards) {
        return hasAPair(cards) && !hasAHighCard(cards);
    }

    public static boolean hasAPair(List<GameCard> cards) {
        return Objects.equals(cards.get(0).getRank(), cards.get(1).getRank());
    }

    public static boolean hasAHighCard(List<GameCard> cards) {
        return cards.stream().anyMatch(card -> CardEvaluator.evaluateCard(card.getRank()) >= 10);
    }

    public static boolean hasTwoHighCards(List<GameCard> cards) {
        return cards.stream().allMatch(card -> CardEvaluator.evaluateCard(card.getRank()) >= 10);
    }

    public static boolean hasAnAce(List<GameCard> cards) {
        return cards.stream().anyMatch(card -> CardEvaluator.evaluateCard(card.getRank()) == 14);
    }

    public static boolean hasAHighPair(List<GameCard> cards) {
        return hasAPair(cards) && hasAHighCard(cards);
    }

    public static boolean hasTwoSuperHighCards(List<GameCard> cards) {
        return cards.stream().allMatch(card -> CardEvaluator.evaluateCard(card.getRank()) >= 13);
    }

    public static boolean hasSameSuit(List<GameCard> cards) {
        return cards.stream().map(GameCard::getSuit).distinct().toList().size() == 1;
    }

    public static boolean isOurBetLow(GameState gameState) {
        int thirdOfOurMoney = (ownBet(gameState) + ownStack(gameState)) / 3;
        return ownBet(gameState) < thirdOfOurMoney;
    }

    public static boolean hasPossibleStraightFlash(List<GameCard> cards) {
        int rankDifference = Math.abs(CardEvaluator.evaluateCard(cards.get(0).getRank()) -
                CardEvaluator.evaluateCard(cards.get(1).getRank()));
        if (rankDifference > 1) {
            return false;
        }
        if (!Objects.equals(cards.get(0).getSuit(), cards.get(1).getSuit())) {
            return false;
        }
        if (cards.stream().anyMatch(card -> CardEvaluator.evaluateCard(card.getRank()) <= 3)) {
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

    public static int bigBlind(GameState gameState) {
        return gameState.getSmallBlind() * 2;
    }
}
