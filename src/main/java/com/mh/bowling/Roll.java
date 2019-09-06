package com.mh.bowling;

import lombok.Getter;

import static com.mh.bowling.config.GameAssumptions.MAXIMUM_POINTS;

public class Roll implements IRoll{
    @Getter
    private int pins;

    public Roll(int pins) {
        if (pins < 0 || pins > MAXIMUM_POINTS)
            throw new IllegalArgumentException("Number of points out of range");

        this.pins = pins;
    }

    @Override
    public boolean hasMaxPoints() {
        return pins == MAXIMUM_POINTS;
    }

    public static Roll draw(int points){
        return new Roll(points);
    }

    public static Roll maximum(){
        return new Roll(MAXIMUM_POINTS);
    }
}
