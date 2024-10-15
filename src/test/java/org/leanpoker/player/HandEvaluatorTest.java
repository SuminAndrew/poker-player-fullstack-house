package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.leanpoker.player.evaluator.HandEvaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandEvaluatorTest {
    private static final String INVALID_HAND = "[]";

    private static final String HOLE_CARDS = "[                         \n" +
            "                {\n" +
            "                    \"rank\": \"6\",                    \n" +
            "                    \"suit\": \"hearts\"                \n" +
            "                },\n" +
            "                {\n" +
            "                    \"rank\": \"K\",\n" +
            "                    \"suit\": \"spades\"\n" +
            "                }\n" +
            "            ]";

    private static final String COMMUNITY_CARDS = "[                            \n" +
            "        {\n" +
            "            \"rank\": \"4\",\n" +
            "            \"suit\": \"spades\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"rank\": \"A\",\n" +
            "            \"suit\": \"hearts\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"rank\": \"6\",\n" +
            "            \"suit\": \"clubs\"\n" +
            "        }\n" +
            "    ]";

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testEvaluateHand() throws JsonProcessingException {
        assertTrue(
                HandEvaluator.evaluateHand(MAPPER.readTree(HOLE_CARDS), MAPPER.readTree(COMMUNITY_CARDS)) >= 0
        );
    }

    @Test
    public void testEvaluateHand_invalidHand() throws JsonProcessingException {
        assertEquals(-1, HandEvaluator.evaluateHand(MAPPER.readTree(INVALID_HAND), MAPPER.readTree(COMMUNITY_CARDS)));
        assertEquals(-1, HandEvaluator.evaluateHand(MAPPER.readTree(HOLE_CARDS), MAPPER.readTree(INVALID_HAND)));
    }
}
