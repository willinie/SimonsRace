package com.wu.simonsrace.structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void generateBoardCorrectly()
    {
        var board = new Board(7,4);
        assertEquals(9, board.boardLength);
        assertEquals(4, board.boardWidth);
    }

    @Test
    public void gridLengthZero()
    {
        assertThrows(Exception.class, () -> new Board(0, 4));

    }

    @Test
    public void gridLengthOverTen()
    {
        assertThrows(Exception.class, () -> new Board(11, 4));

    }

    @Test
    public void boardWidthNegative()
    {
        assertThrows(Exception.class, () -> new Board(7, -1));

    }

    @Test
    public void boardWidthOverSix()
    {
        assertThrows(Exception.class, () -> new Board(7, 7));

    }
}