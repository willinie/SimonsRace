package com.wu.simonsrace.elements.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void generatePlayer(){
        var player = new Player(0, 0, 1);
        assertEquals(0, player.positionX);
        assertEquals(0, player.positionY);
        assertEquals(1, player.id);
    }
}