package snakesandladders;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Chaviaras Michalis
 * This class shows the basic attributes of a player to the ActionPanel, the color and the name and if 
 * it is the player's turn 
 */
public class PlayersInfoPanel extends JPanel {

    /**
     * postcondition:creates a JPnael corresponds to the player "player"
     * @param "player" the player of whom the attributes will be showed 
     * @param "pointer" if the player plays at this turn then the "pointer" is 1, otherwise 0
     * @param "i" the position of the player which is taken from the snakksandladds
     */
    public PlayersInfoPanel(Player player, int i, int pointer) {
        setLayout(new GridLayout(0, 3));
        JLabel label = new JLabel(player.getName());
        label.setVisible(true);
        add(label);

        JPanel color = new JPanel();
        color.setBackground(player.getColor());
        color.setVisible(true);
        add(color);
        if (pointer == i) {
            JPanel paizo = new JPanel();
            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            paizo.setBorder(blackline);
            paizo.setBackground(Color.WHITE);
            paizo.add(new JLabel("Play"));
            paizo.setVisible(true);
            add(paizo);
        } else {
            JPanel paizo = new JPanel();
            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            paizo.setBorder(blackline);
            paizo.setBackground(Color.WHITE);
            paizo.setVisible(true);
            add(paizo);
        }
    }
}
