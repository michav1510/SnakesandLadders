package snakesandladders;

import java.util.Random;

/**
 *
 * @author Mihalis Chaviaras
 */
public class Dice {

    private int sides;

    /**
     * Precondition: 1<sides<inf(diladi einai thetikos arithmos)
     * Postcondition: Creates a dice with "sides" number of sides
     * @param "sides" The number of sides
     */
    public Dice(int sides) {
        this.sides = sides;
    }
    /**
     * @return the result of throwing the dice
     */
    public int rolldice() {
        Random n = new Random();
        int num = (n.nextInt(sides));
        num++;
        return num;
    }
}
