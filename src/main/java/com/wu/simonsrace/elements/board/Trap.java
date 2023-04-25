package com.wu.simonsrace.elements.board;

import com.wu.simonsrace.structure.Board;

public class Trap extends Element{
    public int timer;

    public Trap(int positionX, int positionY){
        super(positionX, positionY);
        this.symbol = "T";
        timer = 0;
    }

}
