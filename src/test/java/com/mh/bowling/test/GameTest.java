package com.mh.bowling.test;

import com.mh.bowling.Game;
import com.mh.bowling.IGame;
import com.mh.bowling.exceptions.EndGameException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.stream.IntStream;

import static com.mh.bowling.config.GameAssumptions.MAXIMUM_POINTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class GameTest {
    private int numberOfShots = 10;
    private int[] regularPinsSet = new int[]{4, 5};
    private int[] sparePinsSet = new int[]{5, 5};

    private int regularPinsSum = IntStream.of(regularPinsSet).sum();
    private int sparePinsSum = IntStream.of(sparePinsSet).sum();

    @Test
    void test01RegularGame() {
        IGame game = new Game();

        for (int i = 0; i < numberOfShots; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        assertEquals(regularPinsSum * numberOfShots, game.score(), "Game score shall match");
    }

    @Test
    void test02FirstSpare() {
        IGame game = new Game();

        game.roll(sparePinsSet[0]);
        game.roll(sparePinsSet[1]);

        for (int i = 0; i < numberOfShots - 1; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        int expectedScore = regularPinsSum * (numberOfShots - 1) + sparePinsSum + regularPinsSet[0];

        assertEquals(expectedScore, game.score(), "Game score shall match");
    }

    @Test
    void test03FirstStrike() {
        IGame game = new Game();

        game.roll(MAXIMUM_POINTS);

        for (int i = 0; i < numberOfShots - 1; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        int expectedScore = regularPinsSum * numberOfShots + MAXIMUM_POINTS;

        assertEquals(expectedScore, game.score(), "Game score shall match");
    }

    @Test
    void test04LastSpare() {
        int lastShot = 6;

        IGame game = new Game();

        for (int i = 0; i < numberOfShots - 1; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        game.roll(sparePinsSet[0]);
        game.roll(sparePinsSet[1]);
        game.roll(lastShot);

        int expectedScore = regularPinsSum * (numberOfShots - 1) + sparePinsSum + lastShot;

        assertEquals(expectedScore, game.score(), "Game score shall match");
    }

    @Test
    void test05LastStrike() {
        IGame game = new Game();

        for (int i = 0; i < numberOfShots - 1; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        game.roll(MAXIMUM_POINTS);
        game.roll(regularPinsSet[0]);
        game.roll(regularPinsSet[1]);

        int expectedScore = regularPinsSum * numberOfShots + MAXIMUM_POINTS;
        assertEquals(expectedScore, game.score(), "Game score shall match");
    }

    @Test
    void test06LastStrikeTriple() {
        IGame game = new Game();

        for (int i = 0; i < numberOfShots - 1; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        for (int i = 0; i < 3; i++) {
            game.roll(MAXIMUM_POINTS);
        }

        int expectedScore = regularPinsSum * (numberOfShots - 1) + MAXIMUM_POINTS * 3;
        assertEquals(expectedScore, game.score(), "Game score shall match");
    }

    @Test
    void test07EndGame() {
        IGame game = new Game();

        for (int i = 0; i < numberOfShots; i++) {
            game.roll(regularPinsSet[0]);
            game.roll(regularPinsSet[1]);
        }

        assertThrows(EndGameException.class, () -> game.roll(MAXIMUM_POINTS));
    }

    @Test
    void test08AllStrikes() {
        IGame game = new Game();

        for (int i = 0; i < numberOfShots + 2; i++) {
            game.roll(MAXIMUM_POINTS);
        }

        assertEquals(300, game.score(), "Game score shall match");
    }
}
