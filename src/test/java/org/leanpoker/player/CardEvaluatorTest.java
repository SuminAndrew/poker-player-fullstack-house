package org.leanpoker.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardEvaluatorTest {

    @Test
    void testEvaluateAce() {
        Assertions.assertEquals(14, CardEvaluator.evaluateCard("A"));
    }

    @Test
    void testEvaluateKing() {
        Assertions.assertEquals(13, CardEvaluator.evaluateCard("K"));
    }

    @Test
    void testEvaluateQueen() {
        Assertions.assertEquals(12, CardEvaluator.evaluateCard("Q"));
    }

    @Test
    void testEvaluateJack() {
        Assertions.assertEquals(11, CardEvaluator.evaluateCard("J"));
    }

    @Test
    void testEvaluateTen() {
        Assertions.assertEquals(10, CardEvaluator.evaluateCard("10"));
    }

    @Test
    void testEvaluateNine() {
        Assertions.assertEquals(9, CardEvaluator.evaluateCard("9"));
    }

    @Test
    void testEvaluateEight() {
        Assertions.assertEquals(8, CardEvaluator.evaluateCard("8"));
    }

    @Test
    void testEvaluateSeven() {
        Assertions.assertEquals(7, CardEvaluator.evaluateCard("7"));
    }

    @Test
    void testEvaluateSix() {
        Assertions.assertEquals(6, CardEvaluator.evaluateCard("6"));
    }

    @Test
    void testEvaluateFive() {
        Assertions.assertEquals(5, CardEvaluator.evaluateCard("5"));
    }

    @Test
    void testEvaluateFour() {
        Assertions.assertEquals(4, CardEvaluator.evaluateCard("4"));
    }

    @Test
    void testEvaluateThree() {
        Assertions.assertEquals(3, CardEvaluator.evaluateCard("3"));
    }

    @Test
    void testEvaluateTwo() {
        Assertions.assertEquals(2, CardEvaluator.evaluateCard("2"));
    }
}
