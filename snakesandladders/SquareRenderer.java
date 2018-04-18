package snakesandladders;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Mihalis Chaviaras
 * This class help to disconnect the graphics from the model of the game
 */
public class SquareRenderer extends JPanel implements ModelListener {

    private SimpleSquareModel box;
    private JPanel south;
    private JPanel center;
    private JPanel north;

    /**
     * Precondition:box!=null
     * @param "box" "this" is the listener of the "box" 
     * 
     */
    public SquareRenderer(SimpleSquareModel box) {
        this.box = box;
    }

    /**
     *
     * @return the SimpleSquareModel "box", "this" is the listener of the "box"
     */
    public SimpleSquareModel getbox() {
        return box;
    }

    /**
     * It paints the JPanel depending on whether there are players on the square
     * if it is an action square of if it the start of the finish 
     */
    public void draw() {
        removeAll();
        setLayout(new GridLayout(2, 0));

	// the north panel
        north = new JPanel();
        north.setBackground(Color.WHITE);
        String temp = this.getbox().getnumberofsquare() + "";
        JLabel label_north = new JLabel(temp);
        label_north.setAlignmentX(Component.LEFT_ALIGNMENT);
        north.add(label_north);
        add(north);

        if (box.getplayers() == null || box.getplayers().length == 0) {

            north.setBackground(Color.WHITE);//if there is no player the north must be white
        } else {
            north.setBackground(Color.GRAY);
            Player players[] = box.getplayers();
            for (int i = 0; i < players.length; i++) {
                MyPawn pawn = new MyPawn(players[i]);
                north.add(pawn);
            }


        }
        if (box instanceof randomadjustment) {
	    //the south panel
            south = new JPanel();
            south.setLayout(new GridLayout(3, 1));
            JLabel label_north1 = new JLabel("Random ");
            JLabel label_north2 = new JLabel("Adjustment");
            south.add(label_north1);
            south.add(label_north2);
            south.setBackground(Color.BLUE);

            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            setBorder(blackline);
            setBackground(Color.WHITE);
//            add(north);
            add(south);
        } else if (box instanceof moveX2) {
//            setLayout(new GridLayout(2, 0));
//
            // the north panel
            north = new JPanel();
            north.setBackground(Color.WHITE);
            String temp1 = box.getnumberofsquare() + "";
            JLabel label_north1 = new JLabel(temp1);
            label_north1.setAlignmentX(Component.LEFT_ALIGNMENT);
            north.add(label_north1);



            south = new JPanel();
            south.setLayout(new GridLayout(3, 1));
            JLabel label_north2 = new JLabel("MoveX2");

            south.add(label_north2);

            south.setBackground(Color.ORANGE);

            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            setBorder(blackline);
            setBackground(Color.WHITE);
//            add(north);
            add(south);


        } else if (box instanceof action) {

            action temp1 = (action) box;
            if (box.getnumberofsquare() > temp1.get_where()) {//then it is a snake 
                south = new JPanel();
                south.setLayout(new GridLayout(3, 1));
                JLabel label_north1 = new JLabel("         GoTo");
                JLabel label_north2 = new JLabel("           " + temp1.get_where());
                south.add(label_north1);
                south.add(label_north2);
                south.setBackground(Color.RED);

                Border blackline;
                blackline = BorderFactory.createLineBorder(Color.black);
                setBorder(blackline);
                setBackground(Color.WHITE);
//                add(north);
                add(south);

            } else { //einai ladder
                south = new JPanel();
                south.setLayout(new GridLayout(3, 1));
                JLabel label_north1 = new JLabel("         GoTo");
                JLabel label_north2 = new JLabel("           " + temp1.get_where());
                south.add(label_north1);
                south.add(label_north2);
                south.setBackground(Color.GREEN);

                Border blackline;
                blackline = BorderFactory.createLineBorder(Color.black);
                setBorder(blackline);
                setBackground(Color.WHITE);
//                add(north);
                add(south);

            }
        } else if (box.getnumberofsquare() == 1) {

            removeAll();
            setLayout(new GridLayout(2, 0));
            north = new JPanel();
            north.setBackground(Color.GREEN);

//          DEBUGGING
            south = new JPanel();
            
            if (box.getplayers() == null || box.getplayers().length == 0) {

            south.setBackground(Color.WHITE);//if there is no player the north must be white 
        } else {
            south.setBackground(Color.GRAY);
            Player players[] = box.getplayers();
            for (int i = 0; i < players.length; i++) {
                MyPawn pawn = new MyPawn(players[i]);
                south.add(pawn);
            }


        }
//          DEBUGGING
            add(south);
            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            setBorder(blackline);
            setBackground(Color.GREEN);
            JLabel start = new JLabel("START!!");
            add(start);


        } else if (box.getnumberofsquare() == Board.columns * Board.rows) {

            removeAll();
            north = new JPanel();
            north.setLayout(new GridLayout(4, 4));
            north.setBackground(Color.WHITE);
            String temp_string = box.getnumberofsquare() + "";
            JLabel label_south = new JLabel(temp_string);
            label_south.setAlignmentX(Component.LEFT_ALIGNMENT);
            north.add(label_south);

            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            setBorder(blackline);
            setBackground(Color.RED);
            JLabel start = new JLabel("FINISH!!");
            add(start);
        } else {
            setLayout(new GridLayout(2, 0));




            //            fitaxno to south
            south = new JPanel();
            if (box.getplayers() == null || box.getplayers().length == 0) {
                south.setBackground(Color.WHITE);
            } else {
                south.setBackground(Color.GRAY);
            }
            Border blackline;
            blackline = BorderFactory.createLineBorder(Color.black);
            setBorder(blackline);
            setBackground(Color.WHITE);
//            add(north);
            add(south);
        }
        this.repaint();
        this.setVisible(true);
    }

    /**
     * Postcondition: Repaints the square because someone maybe left the square
     * or enter to it
     */
    public void modelchanged() {
//        this.removeAll();
        draw();
    }
}
