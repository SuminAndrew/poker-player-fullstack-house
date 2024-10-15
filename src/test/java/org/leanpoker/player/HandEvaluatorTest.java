package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.leanpoker.player.evaluator.Card;
import org.leanpoker.player.evaluator.Hand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.leanpoker.player.evaluator.Card.ACE;
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
    public void testEvaluateHandThreeOfAKindMedium() throws JsonProcessingException {
        assertEquals(
                1959,
                Hand.evaluate(new Card[]{new Card(NINE, CLUBS), new Card(NINE, HEARTS),
                        new Card(NINE, SPADES), new Card(KING, SPADES), new Card(TREY, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandThreeOfAKindHigh() throws JsonProcessingException {
        assertEquals(
                1619,
                Hand.evaluate(new Card[]{new Card(ACE, CLUBS), new Card(ACE, HEARTS),
                        new Card(ACE, SPADES), new Card(KING, SPADES), new Card(TREY, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandFullHouse() throws JsonProcessingException {
        assertEquals(
                273,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(SIX, SPADES), new Card(SIX, CLUBS), new Card(SIX, DIAMONDS)})
        );
    }
}
