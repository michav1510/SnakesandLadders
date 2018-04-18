package snakesandladders;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Chaviaras Michalis
 */
public class MyPawn extends JPanel {

    private Player player;//the player we want to be depicted 
    /**
     * Precondition: p!=null
     * @param "p" the player we want to be depicted
     * we must know the color of the panel 
     */
    public MyPawn(Player p) {
        if (p == null) {
            System.out.println("The argument of MyPawn() is null");;
        } else {
            player = p;
//        Dimension d =new Dimension(500,500);
//        setPreferredSize(d);
            setBackground(player.getColor());
        }
    }

}
