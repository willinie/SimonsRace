package com.wu.simonsrace.elements.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepDiceTest {

    @Test
    public void rollStepDice(){
        var dice = new StepDice();
        int step = dice.rollStep();
        if(step > 4 || step < 1)
            fail();
    }

}