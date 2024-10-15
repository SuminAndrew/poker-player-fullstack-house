package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.leanpoker.player.evaluator.HandEvaluator;

public class HandEvaluatorTest {
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

    @Test
    public void testEvaluateHand() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode holeCards = mapper.readTree(HOLE_CARDS);
        JsonNode communityCards = mapper.readTree(COMMUNITY_CARDS);

        HandEvaluator.evaluateHand(holeCards, communityCards);
    }
}
