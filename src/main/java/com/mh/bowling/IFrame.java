package com.mh.bowling;

public interface IFrame {
    void roll(IRoll roll);
    void bonus(IRoll roll);
    boolean completed();
    int totalPoints();
    boolean isStrike();
    boolean isSpare();
}
