package snakesandladders;

import java.util.LinkedList;

/**
 *  @author Mihalis Chaviaras
 *  This class represents the action square "moveX2"
 *  The colors of the square of type "moveX2" are ORANGE 
 */
public class moveX2 extends SimpleSquareModel {

    /**
     * Precondition:players!=null
     * @param "x" in which column is located on the mesh
     * @param "y" in which row is located on the mesh
     * @param "numberofsquare" the position of the square 
     * @param "players" how many players are located on this square
     */
    public moveX2(int numberofsquare,Player [] players){
        super(numberofsquare,players);
        
    }

    /**
     * Precondition:p!=null
     * @param "p" the player on whom the square will act
     */
    public void action(Player p) {
        p.setmoveX2(3);
    }
}
