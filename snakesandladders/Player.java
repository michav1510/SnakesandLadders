package snakesandladders;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Mihalis Chaviaras
 */
public class Player {

    private String name;
    private Color player_color;
    private SimpleSquareModel position;
    private SimpleSquareModel[] player_history;
    private int moveX2;
    private Stack<SimpleSquareModel> undo;// oi dio autes stoives mas voithane gia na ilopoiisoume
    private Stack<SimpleSquareModel> redo;//ta undo kai redo
    private Player[] all_the_players;//This variable allows us to implement the 'random adjustment' square because 
    // the action of this square applies to all players. It rearranges all the players randomly
 
    /**
     * Precondition: "name","c"!= null
     * Postcondition: creation of a player with name "name" and color "c" 
     * @param "name" The name of the player
     * @param "c" The color of the player
     */
    public Player(String name, Color c) {
        if (name == null || c == null) {
            System.out.println("Some of the arguments of the constructor of the players class are null");
        } else {
            this.name = name;
            player_color = c;
            moveX2 = 0;
            all_the_players = new Player[0];
            undo = new Stack();
            redo = new Stack();
        }
    }

    /**
     * Postcondition: It checks if the "obj" equals with "this" 
     * @param "obj" the object that we will compare it with "this"
     * @return true if the "obj" is of the type "player" and has the same name and color with "this"
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player temp = (Player) obj;
            if (this.getName().equals(temp.getName()) && this.getColor().equals(temp.getColor())) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Postcondition: 0<=int<=3
     * @return the variable moveX2, 0<=moveX2<= 3
     */
    public int getmoveX2() {
        return moveX2;
    }

    /**
     * @return the stack "redo" because this is performed to the SnaksandLadds
     */
    public Stack getRedo() {
        return redo;
    }

    /**
     * Precondition : "undo"!= null
     * Postcondition: Assigns the stack of the arguments equals to "this" stack
     * @param "undo" the stack that we want as an undo stack for "this" player
     */
    public void Setundo(Stack undo) {
        if (undo == null) {
            System.out.println("Setundo took null argument");
        } else {
            this.undo = undo;
        }
    }

    /**
     * Clears the stack "redo" of "this" player 
     */
    public void EmptytheRedo() {
        redo = new Stack();
    }

    /**
     * Precondition: 0<= n <=3
     * Postcondition: assigns the vaule n to the variable moveX2 of "this" player
     * @param "n" how much the moveX2 variable will be 
     */
    public void setmoveX2(int n) {
        moveX2 = n;
    }

    /**
     * 
     * @return in which square the player is located
     */
    public SimpleSquareModel getPosition() {
        return position;
    }

    /**
     * Postcondition: the players is relocated to another square 
     * @param at which square we want the player to go  
     */
    public void moveto(SimpleSquareModel position) {
        try {
            if (position == null) {
                throw new Exception("The method moveto() in the class Player took null argument");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.position = position;

    }

    /**
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the color of the player
     */
    public Color getColor() {
        return player_color;
    }

    /**
     *  Precondition: "player"!= null
     *  Postcondition: insert the "player" to the array with all the players namely "all_the_players"
     * @param "player" the player will be inserted to the array of all the players
     * 
     */
    public void add_all_the_players(Player player) {
        try {
            if (player == null) {
                throw new Exception("The method add_all_the_players() of the class Player took null argument");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (all_the_players.length == 0) {
            all_the_players = new Player[1];
            all_the_players[0] = player;
        } else {
            Player[] temp_players = new Player[all_the_players.length + 1];
            for (int i = 0; i < all_the_players.length; i++) {
                temp_players[i] = all_the_players[i];
            }
            temp_players[all_the_players.length] = player;//we insert the "player" we took as argument at the end
            // of the array
        }
    }

    /**
     * 
     * @return the array with all the players 
     */
    public Player[] get_all_the_players() {
        return all_the_players;
    }

    /**
     * Precondition: box!= null
     * @param "box" is the square which will be added to the history
     */
    public void addHistory(SimpleSquareModel box) {
        try {
            if (box == null) {
                throw new Exception("The addHistory()in the class Player took null argument");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (player_history == null) {
            player_history = new SimpleSquareModel[1];
            player_history[0] = box;
            undo = new Stack();
            undo.push(player_history[0]);
        } else {
            SimpleSquareModel[] temp_history = new SimpleSquareModel[player_history.length + 1];
            for (int i = 0; i < player_history.length; i++) {
                temp_history[i] = player_history[i];
            }
            temp_history[player_history.length] = box;//we will place the "box" to the last position of the array
            player_history = temp_history;
            undo = new Stack();
            for (int i = 0; i < player_history.length; i++) {
                undo.push(player_history[i]);
            }


        }


    }

    /**
     * 
     * @param "box" the box will be added to the stack "redo" 
     */
    public void addtoredo(SimpleSquareModel box) {
        redo.push(box);
    }

    /**
     *
     * @return the "undo" stack is returned in order to the action undo performed from the snakksandladds
     */
    public Stack getundo() {
        return undo;
    }

    /**
     *
     * @return ths history of "this" player.  
     */
    public SimpleSquareModel[] getHistory() {
        return player_history;
    }

    /**
     * @return the array of the players 
     */
    public Player[] getPlayers() {
        return all_the_players;
    }
}
