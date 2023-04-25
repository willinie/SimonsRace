package com.wu.simonsrace.elements.board;

import com.wu.simonsrace.structure.Board;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Players {
    private final ArrayList<Player> players;

    public static Players gnrtPlayers(Board board, int playerNum)
    {
        if(board.boardWidth < playerNum) throw new IllegalArgumentException();
        // Initialize Players object
        var players = new Players();
        for(int i = 0; i < playerNum; i++)
        {
            //Generate the player position randomly
            Random playerPosGnrtr = new Random();
            //Generate random int range from [0, boardWidth)
            int positionY = playerPosGnrtr.nextInt(board.boardWidth);
            //when the position is already taken by other player, re-generate the position
            for (int j = 0; j < players.size(); j++)
            {
                if (players.get(j).positionY == positionY){
                    j = -1;
                    positionY = playerPosGnrtr.nextInt(board.boardWidth);
                }
            }
            Player player = new Player(board.boardLength - 1, positionY, i+1);
            players.add(player);
        }
        return players;
    }
    public Players(){
        players = new ArrayList<>();
    }

    public int size(){
        return players.size();
    }

    public Player get(int i){
        return players.get(i);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void add(Player p){
        for(Player c: players)
        {
            if(p.id == c.id)
            {
                throw new IllegalArgumentException();
            }
        }
        players.add(p);
    }
}
