package com.wu.simonsrace.game;

import com.wu.simonsrace.elements.board.*;
import com.wu.simonsrace.elements.dice.*;
import com.wu.simonsrace.structure.*;

import java.util.Objects;

/**
 * This class defines all the tool methods using in game logic
 */
public class GameUtil {
    /**
     *
     * @param direction left/right/forward/backward
     * @param step step from current index
     * @param p current player
     * @return an integer list representing target position
     */
    public static int[] getTarget(DirectionDice.Direction direction, int step, Player p){
        switch (direction)
        {
            case FORWARD:
                return new int[]{p.positionX - step, p.positionY};
            case BACKWARD:
                return new int[]{p.positionX + step, p.positionY};
            case LEFT:
                return new int[]{p.positionX, p.positionY - step};
            case RIGHT:
                return new int[]{p.positionX, p.positionY + step};
            case MISS:
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param target position to be verified
     * @param board board object
     * @param players players object
     * @return boolean judge if the target position is available
     */
    public static boolean verifyTarget(int[] target, Board board, Players players)
    {
        // Judge if target is out of the board
        if (target[0] < 0 || target[0] >= board.boardLength
                || target[1] < 0 || target[1] >= board.boardWidth)
            return false;
        else if (!isEmpty(board, target)){
            return false;
        }
        else{
            // Judge if there's a player on the grid
            for (Player player: players.getPlayers())
            {
                if(player.positionX == target[0] && player.positionY == target[1])
                    return false;
            }
            return true;
        }
    }

    public static boolean isEmpty(Board board, int[] target)
    {
        String symbol = board.getElement(target[0], target[1]);
        // If grid is empty or start or end return true;
        return Objects.equals(symbol, "x") || Objects.equals(symbol, "S") || Objects.equals(symbol, "E") || Objects.equals(symbol, "T");
    }

    public static boolean isEnd(Board board, int[] target)
    {
        String symbol = board.getElement(target[0], target[1]);
        // If grid is empty or start or end return true;
        return Objects.equals(symbol, "E");
    }

}
