package org.leanpoker.player;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.stream.StreamSupport;

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

    public static int ownBet(JsonNode request) {
        JsonNode players = request.get("players");
        return StreamSupport.stream(players.spliterator(), false)
                .filter(jsonNode -> isOwnPlayer(jsonNode))
                .findAny()
                .map(jsonNode -> jsonNode.get("bet").asInt())
                .orElse(0);
    }

    private static boolean isOwnPlayer(JsonNode player) {
        return Player.VERSION.equals(player.get("version"));
    }
}
