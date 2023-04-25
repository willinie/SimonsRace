package com.wu.simonsrace.game;

import com.wu.simonsrace.GameController;
import com.wu.simonsrace.elements.board.Player;
import com.wu.simonsrace.elements.board.Players;
import com.wu.simonsrace.elements.dice.DirectionDice;
import com.wu.simonsrace.structure.Board;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilTest {

    @Test
    public void getTargetTest(){
        Player player = new Player(5,5,1);
        int[] target = GameUtil.getTarget(DirectionDice.Direction.LEFT, 1, player);
        assertEquals(4, target[1]);
    }

    @Test
    public void validateTargetTest(){
        for(int i = 0; i < 100; i++) {
            Board board = Board.gnrtBoard(7, 4);
            Players players = Players.gnrtPlayers(board, 3);
            board.printBoard_CLI(players);
            int[] target = GameUtil.getTarget(DirectionDice.Direction.FORWARD, 1, players.get(0));
            if(Objects.equals(board.getElement(target[0], target[1]), "x"))
                assertTrue(GameUtil.verifyTarget(target, board, players));
            if(!Objects.equals(board.getElement(target[0], target[1]), "x"))
                assertFalse(GameUtil.verifyTarget(target, board, players));
        }


    }

}