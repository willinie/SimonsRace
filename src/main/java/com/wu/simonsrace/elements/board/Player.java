package com.wu.simonsrace.elements.board;

import java.util.ArrayList;

public class Player extends Element{
    //One and only ID of player
    public int id;

    public Player(int positionX, int positionY, int id)
    {
        super(positionX, positionY);
        //The player will start on (positionX, positionY)
        this.id = id;
        this.symbol = "player";
    }

    public int getId() {
        return id;
    }

    public void setPosition(int[] target)
    {
        this.positionX = target[0];
        this.positionY = target[1];
    }
    public int[] getPosition()
    {
        return new int[]{this.positionX, this.positionY};
    }
}
