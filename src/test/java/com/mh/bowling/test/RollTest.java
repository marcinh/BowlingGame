package com.mh.bowling.test;

import com.mh.bowling.IRoll;
import com.mh.bowling.Roll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class RollTest {
    @Test
    void test01PointsInRange(){
        int numberOfPins = 7;
        IRoll roll = new Roll(numberOfPins);
        assertEquals(numberOfPins, roll.getPins());
        assertFalse(roll.hasMaxPoints());
    }

    @Test
    void test02MaximumPoints(){
        assertTrue(Roll.maximum().hasMaxPoints());
    }

    @Test
    void test03MoreThan10Points(){
        assertThrows(IllegalArgumentException.class, ()-> new Roll(11));
    }

    @Test
    void test04LessThan0Points(){
        assertThrows(IllegalArgumentException.class, ()-> new Roll(-1));
    }
}
