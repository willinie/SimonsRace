package com.wu.simonsrace.elements.board;

import com.wu.simonsrace.structure.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayersTest {

    @Test
    public void generateFourPlayers(){
        Board board = new Board(7, 4);
        assertDoesNotThrow(() -> Players.gnrtPlayers(board, 4));
    }

    @Test
    public void generateFivePlayers(){
        Board board = new Board(7, 4);
        assertThrows(Exception.class, () -> Players.gnrtPlayers(board, 5));
    }

}