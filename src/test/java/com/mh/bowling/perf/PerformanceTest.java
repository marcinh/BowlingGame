package com.mh.bowling.perf;

import com.mh.bowling.Frame;
import com.mh.bowling.Game;
import com.mh.bowling.IGame;
import com.mh.bowling.Roll;
import com.mh.bowling.performance.PerformanceCounter;
import org.junit.jupiter.api.Test;

import static com.mh.bowling.perf.Expectation.GAME_TIMEOUT;
import static com.mh.bowling.perf.Expectation.ROLL_TIMEOUT;
import static org.assertj.core.api.Assertions.assertThat;

class PerformanceTest {
    @Test
    void test01Frame() {
        long duration = PerformanceCounter.within(() -> new Frame(Roll.draw(7)));

        assertThat(duration).isLessThan(ROLL_TIMEOUT);
    }

    @Test
    void test02Game() {
        int[] regularPinsSet = new int[]{4, 5};

        IGame game = new Game();

        long duration = PerformanceCounter.within(() -> {
            for (int i = 0; i < 10; i++) {
                game.roll(regularPinsSet[0]);
                game.roll(regularPinsSet[1]);
            }
        });

        assertThat(duration).isLessThan(GAME_TIMEOUT);
    }
}
