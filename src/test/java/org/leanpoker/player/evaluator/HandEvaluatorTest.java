package org.leanpoker.player.evaluator;

import org.junit.jupiter.api.Test;
import org.leanpoker.player.protocol.GameCard;
import org.leanpoker.player.protocol.Suit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.leanpoker.player.evaluator.Card.*;

public class HandEvaluatorTest {
    private static final List<GameCard> INVALID_HAND = List.of(
            new GameCard("4", Suit.SPADES), new GameCard("K", Suit.SPADES));
    private static final List<GameCard> COMMUNITY_CARDS = List.of(
            new GameCard("4", Suit.SPADES), new GameCard("A", Suit.HEARTS), new GameCard("10", Suit.CLUBS));

    @Test
    public void testEvaluateHandLowPair() {
        assertEquals(
                5913,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(FIVE, SPADES), new Card(NINE, SPADES), new Card(TEN, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandWith6Cards() {
        assertEquals(
                2625,
                HandEvaluator.evaluateHand(List.of(new GameCard("K", Suit.CLUBS), new GameCard("K", Suit.HEARTS)),
                        List.of(new GameCard("5", Suit.SPADES), new GameCard("9", Suit.SPADES), new GameCard("10", Suit.DIAMONDS),
                        new GameCard("10", Suit.SPADES)))
        );
    }

    @Test
    public void testEvaluateHandWith7Cards() {
        assertEquals(
                216,
                HandEvaluator.evaluateHand(List.of(new GameCard("K", Suit.CLUBS), new GameCard("K", Suit.HEARTS)),
                        List.of(new GameCard("5", Suit.SPADES), new GameCard("9", Suit.SPADES), new GameCard("10", Suit.DIAMONDS),
                                new GameCard("10", Suit.SPADES), new GameCard("10", Suit.HEARTS)))
        );
    }

    @Test
    public void testEvaluateHandThreeOfAKind() {
        assertEquals(
                2383,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(SIX, SPADES), new Card(NINE, SPADES), new Card(TREY, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHand_invalidHand() {
        assertEquals(-1, HandEvaluator.evaluateHand(INVALID_HAND, COMMUNITY_CARDS));
    }

    @Test
    public void testEvaluateHandThreeOfAKindMedium() {
        assertEquals(
                1959,
                Hand.evaluate(new Card[]{new Card(NINE, CLUBS), new Card(NINE, HEARTS),
                        new Card(NINE, SPADES), new Card(KING, SPADES), new Card(TREY, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandThreeOfAKindHigh() {
        assertEquals(
                1619,
                Hand.evaluate(new Card[]{new Card(ACE, CLUBS), new Card(ACE, HEARTS),
                        new Card(ACE, SPADES), new Card(KING, SPADES), new Card(TREY, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandTwoPairs() {
        assertEquals(
                2469,
                Hand.evaluate(new Card[]{new Card(ACE, CLUBS), new Card(ACE, HEARTS),
                        new Card(KING, DIAMONDS), new Card(KING, SPADES), new Card(JACK, DIAMONDS)})
        );
    }

    @Test
    public void testEvaluateHandFullHouse() {
        assertEquals(
                273,
                Hand.evaluate(new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(SIX, SPADES), new Card(SIX, CLUBS), new Card(SIX, DIAMONDS)})
        );
    }

    @Test
    public void testGetCombinations5OutOf6() {
        assertEquals(6,
                HandEvaluator.getAllCombinations(
                new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS),
                        new Card(SIX, SPADES), new Card(SIX, CLUBS), new Card(SIX, DIAMONDS), new Card(SIX, HEARTS)},
                5
        ).size());
    }

    @Test
    public void testGetCombinations5OutOf7() {
        assertEquals(21,
                HandEvaluator.getAllCombinations(
                new Card[]{new Card(TREY, CLUBS), new Card(TREY, HEARTS), new Card(DEUCE, HEARTS),
                        new Card(SIX, SPADES), new Card(SIX, CLUBS), new Card(SIX, DIAMONDS), new Card(SIX, HEARTS)},
                5
        ).size());
    }

    @Test
    public void testGetCombinationsStraight() {
        assertEquals(1,
                HandEvaluator.getAllCombinations(
                new Card[]{new Card(ACE, CLUBS), new Card(DEUCE, HEARTS), new Card(TREY, HEARTS),
                        new Card(FOUR, SPADES), new Card(FIVE, CLUBS)},
                5
        ).size());
    }

    @Test
    public void testGetCombinationsFlush() {
        assertEquals(1,
                HandEvaluator.getAllCombinations(
                new Card[]{new Card(ACE, CLUBS), new Card(DEUCE, CLUBS), new Card(SEVEN, CLUBS),
                        new Card(FOUR, CLUBS), new Card(FIVE, CLUBS)},
                5
        ).size());
    }
}
