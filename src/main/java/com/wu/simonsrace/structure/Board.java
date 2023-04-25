package com.wu.simonsrace.structure;

import com.wu.simonsrace.elements.board.*;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * This class defines the logic board of the game
 */
public class Board {

    //board size, excluding finish line and player start area
    public int boardLength = 8, boardWidth = 4;
    //Array to store current state of board
    public Element[][] boardArray;
    //player number
    public int playerNumber = 2;

    // Method to add obstacles on board
    public void gnrtObs() {
        Random r = new Random();
        int rowIndex, columnIndex;
        rowIndex = r.nextInt(this.boardLength-3)+1;
        columnIndex = r.nextInt(this.boardWidth);
        Fire fire = new Fire(rowIndex, columnIndex);
        fire.addToBoard(this);
        for(int i = 0; i < 4; i++)
        {
            rowIndex = r.nextInt(this.boardLength-3)+1;
            columnIndex = r.nextInt(this.boardWidth);
            Hole hole = new Hole(rowIndex, columnIndex);
            hole.addToBoard(this);
        }
        for(int i = 0; i < 4; i++)
        {
            rowIndex = r.nextInt(this.boardLength-3)+1;
            columnIndex = r.nextInt(this.boardWidth);
            Trap trap = new Trap(rowIndex, columnIndex);
            trap.addToBoard(this);
        }


//        Fire fire = new Fire(4, 2);
//        Hole hole1 = new Hole(6, 0);
//        Hole hole2 = new Hole(4, 1);
//        Hole hole3 = new Hole(6, 3);
//        Trap trap1 = new Trap(1, 0);
//        Trap trap2 = new Trap(1, 1);
//        Trap trap3 = new Trap(1, 2);
//        Trap trap4 = new Trap(1, 3);
//        Trap trap5 = new Trap(1, 4);
//        Trap trap6 = new Trap(1, 5);
//
//
//        fire.addToBoard(this);
//        hole1.addToBoard(this);
//        hole2.addToBoard(this);
//        hole3.addToBoard(this);
//        trap1.addToBoard(this);
//        trap2.addToBoard(this);
//        trap3.addToBoard(this);
//        trap4.addToBoard(this);
//        trap5.addToBoard(this);
//        trap6.addToBoard(this);

    }

    /**
     *
     * @param gridLength length of the board excluding starting row and ending row, [7-10]
     * @param boardWidth width of the board, [4-6]
     * @return a board with generated obstacles
     */
    public static Board gnrtBoard(int gridLength, int boardWidth)
    {
        var board = new Board(gridLength, boardWidth);
        board.gnrtObs();
        return board;
    }

    public Board(int gridLength, int boardWidth)
    {
        if(gridLength < 7 || gridLength > 10 || boardWidth < 4 || boardWidth > 6)
            throw new IllegalArgumentException("Grid size error!");
        //Add two line of start line and finish line
        //Board including start & finish line actual size is [0 ~ gridLength+1][0 ~ boardWidth-1]
        this.boardLength = gridLength + 2;
        this.boardWidth = boardWidth;
        boardArray = new Element[this.boardLength][this.boardWidth];
        for(int j = 0; j < this.boardWidth; j++)
        {
            boardArray[0][j] = new Finish(0, j);
        }
        for (int i = 1; i < this.boardLength - 1; i++)
        {
            for (int j = 0; j < this.boardWidth; j++)
            {
                boardArray[i][j] = new Empty(i, j);
            }
        }
        for (int j = 0; j < this.boardWidth; j++)
        {
            boardArray[this.boardLength - 1][j] = new Start(this.boardLength - 1, j);
        }
    }

    /**
     *
     * @param i row index of board
     * @param j column index of board
     * @return a character representing what the grid is
     * "x" means empty
     * "f" means fire
     * "h" means hole
     */
    public String getElement(int i, int j)
    {
        Element element = boardArray[i][j];
        if (element != null)
            return element.symbol;
        else throw new IllegalArgumentException("Cannot print: Wrong element type!");
    }
    //set element, return true as success
    public void setElement(int i, int j, Element element)
    {
        boardArray[i][j] = element;
    }

    /**
     * For testing purpose
     * @param players players object
     */
    public void printBoard_CLI(Players players)
    {
        String[][] printedBoard = new String[this.boardLength][this.boardWidth];
        for(int i = 0; i < boardLength; i++)
        {
            for (int j = 0; j < boardWidth; j++)
            {
                printedBoard[i][j] = getElement(i, j);
            }
        }
        for(Player player: players.getPlayers())
        {
            printedBoard[player.positionX][player.positionY] += "(" + player.id + ")";
        }

        System.out.println("------------Board------------");
        for(int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardWidth; j++) {
                System.out.printf("%-5s", printedBoard[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------------------");

    }

}
