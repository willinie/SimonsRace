package com.wu.simonsrace.elements.dice;

import java.util.Random;

/**
 * Dice generating steps randomly
 */
public class StepDice {
    int num;
    Random r;
    public StepDice(){
        r = new Random();
    }

    /**
     *
     * @return random steps [1, 4]
     */
    public int rollStep(){
        // Generate random step in [1, 4]
        return r.nextInt(4)+1;
    }
}
