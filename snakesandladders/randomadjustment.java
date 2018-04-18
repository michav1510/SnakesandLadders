package snakesandladders;

import java.util.LinkedList;

/**
 *
 * @author Mihalis Chaviaras
 *  The squares of this type have BLUE color
 *
 */
public class randomadjustment extends SimpleSquareModel {

     /**
     * Precondition: numberofsquare>0 & players!=null
     * Postcondition: It creates a square with number(position) "numberofsquare" having on it
     * the players in the array "players" and having as listener the "sqrend"
     * 
     * @param "numberofsquare" the position of the square 
     * @param "players" which players are on the square
     */
    public randomadjustment(int numberofsquare, Player [] players) {
        super(numberofsquare,players);
    }

    @Override
    /**
     * Precondition:p!=null
     * @param p o player pano ston opoio tha drasi to tetragono
     */
    public void action(Player p) {

    }
}
