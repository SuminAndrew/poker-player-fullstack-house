package org.leanpoker.player;

import org.leanpoker.player.protocol.GamePlayer;
import org.leanpoker.player.protocol.GameState;

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
        return gameState.getPlayers().stream()
                .filter(Utils::isOwnPlayer)
                .map(GamePlayer::getBet)
                .findAny()
                .orElse(0);
    }

    private static boolean isOwnPlayer(GamePlayer player) {
        return Player.VERSION.equals(player.getVersion());
    }
}
