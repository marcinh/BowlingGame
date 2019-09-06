package com.mh.bowling;

import com.mh.bowling.exceptions.EndGameException;

import java.util.ArrayList;
import java.util.List;

import static com.mh.bowling.config.GameAssumptions.MAXIMUM_FRAMES;

public class Game implements IGame {
    private Frame lastFrame;
    private List<Frame> frames;

    public Game() {
        frames = new ArrayList<>();
    }

    public void roll(int numberOfPins) {
        if (frames.size() == MAXIMUM_FRAMES)
            throw new EndGameException("Game is ended");

        IRoll roll = Roll.draw(numberOfPins);

        frames.forEach(frame -> frame.bonus(roll));

        if (lastFrame == null)
            lastFrame = new Frame(roll, frames.size() == MAXIMUM_FRAMES - 1);
        else
            lastFrame.roll(roll);

        if (!lastFrame.completed())
            return;

        frames.add(lastFrame);
        lastFrame = null;
    }

    public int score() {
        return frames.stream().mapToInt(Frame::totalPoints).sum();
    }
}
