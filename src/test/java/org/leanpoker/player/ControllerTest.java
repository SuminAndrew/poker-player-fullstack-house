package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    private final PlayerController controller = new PlayerController();

    private final String GAME_STATE = "{\n" +
            "  \"players\":[\n" +
            "    {\n" +
            "      \"name\":\"Player 1\",\n" +
            "      \"stack\":1000,\n" +
            "      \"status\":\"active\",\n" +
            "      \"bet\":0,\n" +
            "      \"hole_cards\":[{\"rank\":\"A\",\"suit\":\"clubs\"},{\"rank\":\"A\",\"suit\":\"hearts\"}],\n" +
            "      \"version\":\"Java Bot v1\",\n" +
            "      \"id\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Player 2\",\n" +
            "      \"stack\":1000,\n" +
            "      \"status\":\"active\",\n" +
            "      \"bet\":0,\n" +
            "      \"hole_cards\":[],\n" +
            "      \"version\":\"Version name 2\",\n" +
            "      \"id\":1\n" +
            "    }\n" +
            "  ],\n" +
            "  \"tournament_id\":\"550d1d68cd7bd10003000003\",\n" +
            "  \"game_id\":\"550da1cb2d909006e90004b1\",\n" +
            "  \"round\":0,\n" +
            "  \"bet_index\":0,\n" +
            "  \"small_blind\":10,\n" +
            "  \"minimum_raise\":5,\n" +
            "  \"orbits\":0,\n" +
            "  \"dealer\":0,\n" +
            "  \"community_cards\":[],\n" +
            "  \"current_buy_in\":0,\n" +
            "  \"pot\":0\n" +
            "}";

    private final String SHOWDOWN_GAME_STATE = "{\"tournament_id\":\"670d701627ba530002c5f508\",\"game_id\":\"670e727ed2df1a0002e177f3\",\"round\":6,\"players\":[{\"name\":\"All in\",\"stack\":0,\"status\":\"out\",\"bet\":0,\"time_used\":51788,\"version\":\"Default TypeScript folding player\",\"id\":0},{\"name\":\"Bingo \",\"stack\":0,\"status\":\"out\",\"bet\":0,\"time_used\":14578,\"version\":\"Kotlin Player 0.0.1\",\"id\":1},{\"name\":\"Ace Dalton\",\"stack\":0,\"status\":\"out\",\"bet\":0,\"time_used\":35054,\"version\":\"Default TypeScript folding player\",\"id\":2},{\"name\":\"Fullstack house\",\"stack\":3000,\"status\":\"active\",\"bet\":0,\"hole_cards\":[{\"rank\":\"K\",\"suit\":\"clubs\"},{\"rank\":\"J\",\"suit\":\"hearts\"}],\"time_used\":6930604,\"version\":\"Java Bot v1\",\"amount_won\":6,\"id\":3},{\"name\":\"Spades and Hearts\",\"stack\":0,\"status\":\"out\",\"bet\":0,\"time_used\":4455151,\"version\":\"1.0\",\"id\":4},{\"name\":\"Polyglots\",\"stack\":996,\"status\":\"folded\",\"bet\":0,\"time_used\":93169,\"version\":\"Default TypeScript folding player\",\"id\":5},{\"name\":\"Lucky Fluke\",\"stack\":2004,\"status\":\"folded\",\"bet\":0,\"time_used\":4456130,\"version\":\"B0rk3d AI\",\"id\":6},{\"name\":\"PokerJS\",\"stack\":0,\"status\":\"out\",\"bet\":0,\"time_used\":9791,\"version\":\"Default TypeScript folding player\",\"id\":7}],\"small_blind\":2,\"big_blind\":4,\"orbits\":0,\"dealer\":5,\"community_cards\":[{\"rank\":\"6\",\"suit\":\"spades\"},{\"rank\":\"Q\",\"suit\":\"clubs\"},{\"rank\":\"4\",\"suit\":\"diamonds\"},{\"rank\":\"6\",\"suit\":\"clubs\"},{\"rank\":\"Q\",\"suit\":\"hearts\"}],\"current_buy_in\":0,\"pot\":0}";

    @Test
    public void testBetRequest() {
        Map<String, String> body = new HashMap<>();
        body.put("action", "bet_request");
        body.put("game_state", GAME_STATE);
        try {
            String s = controller.doPost(body);
            Assertions.assertNotNull(s);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testShowdown() {
        Map<String, String> body = new HashMap<>();
        body.put("action", "showdown");
        body.put("game_state", SHOWDOWN_GAME_STATE);
        try {
            String s = controller.doPost(body);
            assertEquals("", s);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testVersion() {
        Map<String, String> body = new HashMap<>();
        body.put("action", "version");
        try {
            String s = controller.doPost(body);
            assertEquals(Player.VERSION, s);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }
    }
}
