package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.leanpoker.player.evaluator.Card;
import org.leanpoker.player.evaluator.Hand;
import org.leanpoker.player.evaluator.HandEvaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.leanpoker.player.evaluator.Card.CLUBS;
import static org.leanpoker.player.evaluator.Card.DIAMONDS;
import static org.leanpoker.player.evaluator.Card.FIVE;
import static org.leanpoker.player.evaluator.Card.HEARTS;
import static org.leanpoker.player.evaluator.Card.KING;
import static org.leanpoker.player.evaluator.Card.NINE;
import static org.leanpoker.player.evaluator.Card.SIX;
import static org.leanpoker.player.evaluator.Card.SPADES;
import static org.leanpoker.player.evaluator.Card.TEN;
import static org.leanpoker.player.evaluator.Card.TREY;

public class HandEvaluatorTest {
    private static final String INVALID_HAND = "[]";

    private static final String HOLE_CARDS = "[                         \n" +
            "                {\n" +
            "                    \"rank\": \"6\",                    \n" +
            "                    \"suit\": \"spades\"                \n" +
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
            "            \"rank\": \"10\",\n" +
            "            \"suit\": \"clubs\"\n" +
            "        }\n" +
            "    ]";

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testEvaluateHand() throws JsonProcessingException {
        assertEquals(
                6285,
                HandEvaluator.evaluateHand(MAPPER.readTree(HOLE_CARDS), MAPPER.readTree(COMMUNITY_CARDS))
        );
    }

    @Test
    public void testEvaluateHandLowPair() throws JsonProcessingException {
        assertEquals(
                5913,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(FIVE, SPADES), new Card(NINE, SPADES), new Card(TEN, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandHighPair() throws JsonProcessingException {
        assertEquals(
                3685,
                Hand.evaluate(new Card[]{new Card(KING, CLUBS), new Card(KING, HEARTS),
                        new Card(FIVE, SPADES), new Card(NINE, SPADES), new Card(TEN, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandTwoPairs() throws JsonProcessingException {
        assertEquals(
                3243,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(SIX, SPADES), new Card(NINE, SPADES), new Card(SIX, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandThreeOfAKind() throws JsonProcessingException {
        assertEquals(
                2383,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(SIX, SPADES), new Card(NINE, SPADES), new Card(TREY, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHand_invalidHand() throws JsonProcessingException {
        assertEquals(-1, HandEvaluator.evaluateHand(MAPPER.readTree(INVALID_HAND), MAPPER.readTree(COMMUNITY_CARDS)));
        assertEquals(-1, HandEvaluator.evaluateHand(MAPPER.readTree(HOLE_CARDS), MAPPER.readTree(INVALID_HAND)));
    }
}
