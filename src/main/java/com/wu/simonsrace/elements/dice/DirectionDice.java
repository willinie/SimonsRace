package com.wu.simonsrace.elements.dice;

import java.util.Random;

/**
 * Dice generating directions randomly
 */
public class DirectionDice {
    /**
     * enum representing directions
     */
    public enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        MISS
    }
    int num;
    Random r;

    public DirectionDice(){
        r = new Random();
    }

    /**
     *
     * @return random direction enum [FORWARD,BACKWARD,MISS]
     */
    public Direction rollDirection(){
        num = r.nextInt(4);
        if(num == 0 || num == 1)
            return Direction.FORWARD;
        else if(num == 2)
            return Direction.BACKWARD;
        else if(num == 3)
            return Direction.MISS;
        else throw new IllegalArgumentException();
    }

}
