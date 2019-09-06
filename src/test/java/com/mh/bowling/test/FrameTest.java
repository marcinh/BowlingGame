package com.mh.bowling.test;

import com.mh.bowling.Frame;
import com.mh.bowling.IFrame;
import com.mh.bowling.Roll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class FrameTest {
    @Test
    void test01RegularSet() {
        int[] numberOfPins = new int[]{2, 7};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        assertFalse(frame.completed(), "Frame is not completed");

        frame.roll(Roll.draw(numberOfPins[1]));
        assertTrue(frame.completed(), "Frame is completed");
        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
        assertFalse(frame.isSpare(), "Frame did not end with spare");
        assertFalse(frame.isStrike(), "Frame did not end with strike");
    }

    @Test
    void test02Spare() {
        int[] numberOfPins = new int[]{5, 5};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        assertFalse(frame.completed(), "Frame is not completed");

        frame.roll(Roll.draw(numberOfPins[1]));
        assertTrue(frame.completed(), "Frame is completed");

        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
        assertTrue(frame.isSpare(), "Frame ended with spare");
        assertFalse(frame.isStrike(), "Frame did not end with strike");
    }

    @Test
    void test03Strike() {
        IFrame frame = new Frame(Roll.maximum());
        assertTrue(frame.completed(), "Frame is completed");
        assertFalse(frame.isSpare(), "Frame did not end with spare");
        assertTrue(frame.isStrike(), "Frame ended with strike");
    }

    @Test
    void test04IllegalSum() {
        int[] numberOfPins = new int[]{4, 7};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        assertThrows(IllegalArgumentException.class, () -> frame.roll(Roll.draw(numberOfPins[1])));
    }

    @Test
    void test05SpareBonusExact() {
        int[] numberOfPins = new int[]{3, 7, 8};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        frame.roll(Roll.draw(numberOfPins[1]));
        frame.bonus(Roll.draw(numberOfPins[2]));

        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
    }

    @Test
    void test06SpareBonusNotCounted() {
        int[] numberOfPins = new int[]{3, 7, 8};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        frame.roll(Roll.draw(numberOfPins[1]));
        frame.bonus(Roll.draw(numberOfPins[2]));
        frame.bonus(Roll.maximum());

        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
    }

    @Test
    void test07StrikeBonusExact() {
        int[] numberOfPins = new int[]{10, 8, 7};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        frame.bonus(Roll.draw(numberOfPins[1]));
        frame.bonus(Roll.draw(numberOfPins[2]));

        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
    }

    @Test
    void test08StrikeBonusNotCounted() {
        int[] numberOfPins = new int[]{10, 7, 8};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]));
        frame.bonus(Roll.draw(numberOfPins[1]));
        frame.bonus(Roll.draw(numberOfPins[2]));
        frame.bonus(Roll.maximum());

        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
    }

    @Test
    void test09LastFrameRegular() {
        int[] numberOfPins = new int[]{5,2};

        IFrame frame = new Frame(Roll.draw(numberOfPins[0]), true);
        assertFalse(frame.completed(), "Frame is not completed");
        frame.roll(Roll.draw(numberOfPins[1]));
        assertTrue(frame.completed(), "Frame is completed");
        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
        assertFalse(frame.isSpare(), "Frame did not end with spare");
        assertFalse(frame.isStrike(), "Frame did not end with strike");
    }

    @Test
    void test10LastFrameSpare() {
        int[] numberOfPins = new int[]{3, 7, 8};
        IFrame frame = new Frame(Roll.draw(numberOfPins[0]), true);
        assertFalse(frame.completed(), "Frame is not completed");

        frame.roll(Roll.draw(numberOfPins[1]));
        assertFalse(frame.completed(), "Frame is not completed");

        frame.roll(Roll.draw(numberOfPins[2]));
        assertTrue(frame.completed(), "Frame is completed");
        assertTrue(frame.isSpare(), "Frame ended with spare");
        assertFalse(frame.isStrike(), "Frame did not end with strike");
        assertEquals(IntStream.of(numberOfPins).sum(), frame.totalPoints(), "Total points should match");
    }

    @Test
    void test11LastFrameStrike() {
        int[] numberOfPins = new int[]{3, 6};

        IFrame frame = new Frame(Roll.maximum(), true);
        assertFalse(frame.completed(), "Frame is not completed");

        frame.roll(Roll.draw(numberOfPins[0]));
        assertFalse(frame.completed(), "Frame is not completed");

        frame.roll(Roll.draw(numberOfPins[1]));
        assertTrue(frame.completed(), "Frame is completed");

        assertFalse(frame.isSpare(), "Frame did not end with spare");
        assertTrue(frame.isStrike(), "Frame ended with strike");
    }
}
