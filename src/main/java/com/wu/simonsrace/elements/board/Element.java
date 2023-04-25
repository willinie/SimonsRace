package com.wu.simonsrace.elements.board;

import com.wu.simonsrace.structure.Board;

/**
 * super class for all nodes in logic game board
 */
public class Element {
    public int positionX, positionY;
    //store type of element
    public String symbol;

    //constructor
    Element(int positionX, int positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * add obstacles object to board
     * @param board board object
     */
    public void addToBoard(Board board)
    {
        board.boardArray[this.positionX][this.positionY] = this;
        if(this instanceof Fire)
        {
            board.boardArray[this.positionX + 1][this.positionY] = this;
        }
    }
}
