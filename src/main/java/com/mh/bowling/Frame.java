package com.mh.bowling;

import java.util.ArrayList;
import java.util.List;

import static com.mh.bowling.config.GameAssumptions.MAXIMUM_POINTS;

public class Frame implements IFrame {
    private List<IRoll> rolls;
    private List<IRoll> bonusRolls;
    private boolean isLast;

    public Frame(IRoll roll, boolean isLast) {
        rolls = new ArrayList<>();
        bonusRolls = new ArrayList<>();
        roll(roll);
        this.isLast = isLast;
    }

    public Frame(IRoll roll) {
        this(roll, false);
    }

    public void roll(IRoll roll) {
        rolls.add(roll);

        if (!isLast && totalPoints() > MAXIMUM_POINTS)
            throw new IllegalArgumentException("Number of points out of range");
    }

    public boolean completed() {
        if ((isStrike() || isSpare()) && isLast)
            return rolls.size() == 3;

        if (isStrike())
            return true;

        return rolls.size() == 2;
    }

    private int basicPoints() {
        return rolls.stream().mapToInt(IRoll::getPins).sum();
    }

    public int totalPoints() {
        return basicPoints() + bonusRolls.stream().mapToInt(IRoll::getPins).sum();
    }

    @Override
    public boolean isStrike() {
        return rolls.size() > 0 && rolls.get(0).getPins() == MAXIMUM_POINTS;
    }

    @Override
    public boolean isSpare() {
        return rolls.size() > 1 && rolls.get(0).getPins() + rolls.get(1).getPins() == MAXIMUM_POINTS;
    }

    public void bonus(IRoll bonusRole) {
        if ((isStrike() && bonusRolls.size() < 2) || (isSpare() && bonusRolls.size() < 1))
            bonusRolls.add(bonusRole);
    }
}
