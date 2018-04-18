package snakesandladders;

import java.awt.Color;
import java.util.LinkedList;

/**
 * @author Mihalis Chaviaras
 * This class represents a square of the mesh 
 * 
 */
public class SimpleSquareModel implements Subject {

    private int numberofsquare; // The position of the square 
    private Player[] players;//which players are on the square
    private ModelListener sqrend;// It is the listerner of type SquareRenderer 
    private Board board;

    /**
     * Precondition: numberofsquare>0 & players!=null
     * Postcondition: It creates a square with number(position) "numberofsquare" having on it
     * the players in the array "players" and having as listener the "sqrend"
     * 
     * @param "numberofsquare" the position of the square 
     * @param "players" which players are on the square
     */
    public SimpleSquareModel(int numberofsquare, Player[] players) {

        this.numberofsquare = numberofsquare;
        this.players = players;
        this.players = new Player[0];

    }

    /**
     *
     * @return the array with the players
     */
    public Player[] getplayers() {
        return players;
    }

    /**
     *  Precondition:sqren!=null
     * @param "sqrend" the object of type ModelListener which is a listener of this square 
     */
    public void setListener(ModelListener sqrend) {
        if (sqrend == null) {
            
        } else {
            this.sqrend = sqrend;
        }
    }

    /**
     *  This method allows us to add the board after the creation of the SimpleSquareModel because 
     *  when the constructor of SimpleSquareModel is called the Board hasn't been instatiated
     *
     * Precondition:to board!=null
     * @param "board" the object of type Board we will add
     */
    public void addboard(Board board) {
        if(board==null){
            System.out.println("The addBoard() took null argument");
        }else{
            this.board = board;
        }
    }

    /**
     * This method will be overriden at the child classes. At this 
     * class the action is nothing.
     * Precondition:p!=null
     * @param "p" the player on whom the square will act 
     */
    public void action(Player p) {
    }

    /**
     * Precondition:p!=null
     * postcondition:insert the player "p" to the array players 
     * @param "p" the player we want to insert to the array
     */
    public void addplayer(Player p) {
        try {
            if (p == null) {
                throw new Exception("i aaddplayer pou vrisketai stin SimpleSquareModel pire"
                        + " orisma null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (players == null) {
            players = new Player[1];
            players[0] = p;
        } else {
            Player[] temp_players = new Player[players.length + 1];
            for (int i = 0; i < players.length; i++) {
                temp_players[i] = players[i];
            }
            temp_players[players.length] = p;
            players = temp_players;
        }

        update();//the SquareRenderer must be informed
    }

    /**
     * This class is needed to be able every child class to get the Board
     * @return the Board board
     */
    public Board getboard() {
        return board;
    }

    /**
     * Postcondition: remove the player "p" from the array with the players
     * @param "p" the player which will be removed
     */
    public void removeplayer(Player p) {
        try {
            if (players == null) {
                throw new Exception("There is no player to be removed from this square");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (players.length == 1) {
            players = null;

        } else {
            boolean flag = false;
            int position = players.length;
            for (int i = 0; i < players.length; i++) {//we find where the player is
                if (players[i].equals(p)) {
                    position = i;//we find the current position of the player in the array
                    flag = true;
                } else {
                }
            }
            if (flag == true) {
                Player[] temp = new Player[players.length - 1];
                int count = 0;
                for (int i = 0; i < players.length; i++) {
                    if (i == position) {
                    } else {
                        temp[count] = players[i];
                        count++;
                    }
                }
                players = temp;
            }

        }

        update();//The SquareRenderer must be informed
    }

    /**
     *
     * @return the position of the square in the mesh
     */
    public int getnumberofsquare() {
        return numberofsquare;
    }

    /**
     * It informs the listeners, in this case the object of type SquareRenderer which
     * is the listener
     */
    public void update() {
        sqrend.modelchanged();
    }
}
