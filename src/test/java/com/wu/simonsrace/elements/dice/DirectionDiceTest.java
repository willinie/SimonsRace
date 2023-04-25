package com.wu.simonsrace.elements.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionDiceTest {
    @Test
    public void rollDirectionDice(){
        DirectionDice directionDice = new DirectionDice();
        var dir = directionDice.rollDirection();
        if (dir != DirectionDice.Direction.MISS &&
        dir != DirectionDice.Direction.FORWARD &&
        dir != DirectionDice.Direction.BACKWARD)
            fail();
    }
}