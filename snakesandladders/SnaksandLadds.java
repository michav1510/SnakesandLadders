package snakesandladders;

import com.sun.org.apache.regexp.internal.REDebugCompiler;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Mihalis Chaviaras
 * SnakksandLadds is the class which uses all the other classes, initializes
 * the game, show who plays, executes the basics actions: play , undo ,redo
 */
public class SnaksandLadds {

    private Player players[];//who are the players
    // first in the array must be the player who plays first
    private Board table; //the mesh of the game 
    private GameMaster master;  
    private int pointer; //show us whos turn is

    // the colors of the players are red, green, blue, pink, orange
    /**
     *  Precondition:names!=null , table!=null, master !=null
     *  in fact this methos starts the game
     *  @param names the names of the players
     *  @param table the mesh of the game
     */
    public SnaksandLadds(String names[], Board table, GameMaster master) {
        try {
            if (names == null) {
                throw new Exception("There are no players name");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.master = master;
        this.table = table;
        pointer = 0;
        players = new Player[names.length];
        Player temp[] = new Player[players.length];
        for (int i = 0; i < names.length; i++) {
            if (i == 0) {//the first player
                players[i] = new Player(names[i], Color.RED);
                temp[i] = new Player(names[i], Color.RED);
            } else if (i == 1) {//second player
                players[i] = new Player(names[i], Color.GREEN);
                temp[i] = new Player(names[i], Color.GREEN);
            } else if (i == 2) {//third player
                players[i] = new Player(names[i], Color.BLUE);
                temp[i] = new Player(names[i], Color.BLUE);
            } else if (i == 3) {// fourth player
                players[i] = new Player(names[i], Color.PINK);
                temp[i] = new Player(names[i], Color.PINK);
            } else if (i == 4) {// fifth player
                players[i] = new Player(names[i], Color.ORANGE);
                temp[i] = new Player(names[i], Color.ORANGE);

            }
        }
        //     I made a temp table to fix the turn of the players

        int zaries[] = new int[players.length];//oi zaries ton paixton

        for (int j = 0; j < zaries.length; j++) {//Every player throws the dice
            Dice dice = new Dice(names.length * 6);
//	  I made a dice with many sides, the reason for this is to avoid 
//	  the big possibility of two or more players to bring the same number
//	  with the dice
            zaries[j] = dice.rolldice();
        }
        //DEBUGGING
        master.setontextarea("The game starts");
        for (int i = 0; i < players.length; i++) {
            master.setontextarea("The player :" + players[i].getName() + "  brought :" + zaries[i]);
        }
        //DEBUGGING
        Player temp_player;
        //below the bubblesort is performed
        int temp_int;
        //the sorting is in ascending order
        for (int k = 0; k < zaries.length; k++) {
            for (int l = zaries.length - 1; l >= k + 1; l--) {
                if (zaries[l - 1] > zaries[l]) {
                    temp_int = zaries[l - 1];
                    zaries[l - 1] = zaries[l];
                    zaries[l] = temp_int;
                    temp_player = temp[l - 1];
                    temp[l - 1] = temp[l];
                    temp[l] = temp_player;
                }
            }
        }


        int count = 0;
        for (int i = players.length - 1; i >= 0; i--) {//put the table in reverse order
//            because it was in ascending order
            players[count] = temp[i];
            count++;
        }

        for (int i = 0; i < players.length; i++) {//sin every player I put all the players 
//           in the table
            for (int j = 0; j < players.length; j++) {
                players[i].add_all_the_players(players[j]);
            }
        }
        for (int i = 0; i < players.length; i++) {//the players must begin from the first square 
            table.getsquare(1).getbox().addplayer(players[i]);
            players[i].moveto(table.getsquare(1).getbox());
//           This must not be in the player's history 
        }
        master.setPlayeronActionPanel(players, pointer); //It will display the players and who's play next 
        //to the action panel

    }

    /**
     * With this methods the player plays, there is no need for an argument 
     * because the SnakksandLadds knows whos plays next through the "pointer"
     */
    public void play() {

        if (players[pointer].getmoveX2() > 0) {//Here is checked if the player has moveX2
            Dice dice = new Dice(6);
            int zaria1 = (dice.rolldice());

            int zaria = 2 * zaria1;
//            DEBUGGING
            master.diksetinzariapouirthe(zaria);
//             DEBUGGING

            if ((players[pointer].getPosition().getnumberofsquare() + zaria) >= table.getsumofsquares()) {
//          Here is the case where someone reaches the finish
                players[pointer].getPosition().removeplayer(players[pointer]);//firstly I must remove the player from the
//current position
                players[pointer].addHistory(players[pointer].getPosition());
                players[pointer].moveto(table.getsquare(table.getsumofsquares()).getbox());
                players[pointer].getPosition().addplayer(players[pointer]);
                players[pointer].setmoveX2(players[pointer].getmoveX2() - 1);


            } else {//This is the case where someone dont reach the finish 
                if (table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox() instanceof moveX2) {
//                  If Someone will go to moveX2 
                    master.setontextarea("o paixtis  " + players[pointer].getName() + "  efere : " + zaria
                            + "  pige stin thesi  : " + table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox().getnumberofsquare());

                    players[pointer].getPosition().removeplayer(players[pointer]);
                    players[pointer].addHistory(players[pointer].getPosition());
                    players[pointer].moveto(table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox());
//                Transports the player where he was plus the dice he brought
                    players[pointer].getPosition().addplayer(players[pointer]);
                    players[pointer].getPosition().action(players[pointer]);//the action will take place
                    players[pointer].setmoveX2(players[pointer].getmoveX2() - 1);
                    //minus 1 because the player played once the moveX2


                } else if (table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox() instanceof action) {
                    String message = ("The player " + players[pointer].getName() + " brought : " + zaria
                            + " went through the " + table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox().getnumberofsquare());
                    players[pointer].getPosition().removeplayer(players[pointer]);
                    players[pointer].addHistory(players[pointer].getPosition());
                    players[pointer].moveto(table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox());
//                stelnei ton paikti ekei pou itan sin tin zaria pou efere
                    players[pointer].getPosition().addplayer(players[pointer]);
                    players[pointer].getPosition().action(players[pointer]);//the action will take place
                    players[pointer].setmoveX2(players[pointer].getmoveX2() - 1);
		    //minus 1 because the player played once the moveX2

                    message = message + " to the position :" + players[pointer].getPosition().getnumberofsquare();
                    master.setontextarea(message);
                } else {
                    String message = ("The player  " + players[pointer].getName() + "  brought :" + zaria + "  went to the position  "
                            + table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox().getnumberofsquare());
                    players[pointer].getPosition().removeplayer(players[pointer]);
                    players[pointer].addHistory(players[pointer].getPosition());
                    players[pointer].moveto(table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox());
//                stelnei ton paikti ekei pou itan sin tin zaria pou efere
                    players[pointer].getPosition().addplayer(players[pointer]);
                    players[pointer].getPosition().action(players[pointer]);//tha ginei to action (pou den kanei tpt epi tis ousias)
                    players[pointer].setmoveX2(players[pointer].getmoveX2() - 1);
		    //minus 1 because the player played once the moveX2
                    master.setontextarea(message);
                }


            }

        } else {//in this case there is no moveX2
            Dice dice = new Dice(6);
            int zaria = dice.rolldice();
            //            DEBUGGING
            master.diksetinzariapouirthe(zaria);
//             DEBUGGING
            if ((players[pointer].getPosition().getnumberofsquare() + zaria) >= table.getsumofsquares()) {
//          eimaste stin periptosi pou kapoios ftanei sto finish
                players[pointer].getPosition().removeplayer(players[pointer]);//firstly we have to remove the player from the current position
                players[pointer].addHistory(players[pointer].getPosition());
                players[pointer].moveto(table.getsquare(table.getsumofsquares()).getbox());
                players[pointer].getPosition().addplayer(players[pointer]);


            } else {//the case where the player don't reach the finish
                if (table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox() instanceof moveX2) {
                    master.setontextarea("o paixtis  " + players[pointer].getName() + "  efere : " + zaria
                            + "  pige stin thesi : " + table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox().getnumberofsquare());

                    players[pointer].getPosition().removeplayer(players[pointer]);
                    players[pointer].addHistory(players[pointer].getPosition());
                    players[pointer].moveto(table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox());
                    players[pointer].getPosition().addplayer(players[pointer]);
                    players[pointer].getPosition().action(players[pointer]);//tha ginei to action


                } else if (table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox() instanceof action) {
                    String message = (" o paixtis " + players[pointer].getName() + " efere : " + zaria
                            + " pige meso tou " + table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox().getnumberofsquare());
                    players[pointer].getPosition().removeplayer(players[pointer]);
                    players[pointer].addHistory(players[pointer].getPosition());
                    players[pointer].moveto(table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox());
                    players[pointer].getPosition().addplayer(players[pointer]);
                    players[pointer].getPosition().action(players[pointer]);//tha ginei to action

                    message = message + " stin thesi :" + players[pointer].getPosition().getnumberofsquare();
                    master.setontextarea(message);
                } else {
                    String message = (" The player  " + players[pointer].getName() + "  brought  " + zaria + "  and went to the position "
                            + table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox().getnumberofsquare());

                    players[pointer].getPosition().removeplayer(players[pointer]);
                    players[pointer].addHistory(players[pointer].getPosition());
                    players[pointer].moveto(table.getsquare(players[pointer].getPosition().getnumberofsquare() + zaria).getbox());
                    players[pointer].getPosition().addplayer(players[pointer]);
                    players[pointer].getPosition().action(players[pointer]);

                    master.setontextarea(message);
                }


            }
        }
//      change the pointer in order to play the next player
        if (pointer == players.length - 1) {
            pointer = 0;
        } else {
            pointer++;
        }
        master.setPlayeronActionPanel(players, pointer); //display the players and who's play next
        getWinner();//after every round it must be checked if there is a winner
    }

    /*
     * Postcondition: Calls the "winner" of the "GameMaster" if there is a winner otherwise not
     * Checks if there is a winner and displays a window with the name of the winner
     */
    public void getWinner() {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getPosition().getnumberofsquare() == table.getsumofsquares()) {
                master.winner(players[i]);
            }
        }
    }

    /**
     * Postcondition: performs the action "undo" to the model of the game
     */
    public void undo() {
        if (pointer == 0) {//prepei na paiksei o proigoumenos
            pointer = players.length - 1;
        } else {
            pointer--;
        }


        if (players[pointer].getundo() == null || players[pointer].getundo().isEmpty()) {
            master.setontextarea("There is no previous moves for this player ");
            if (pointer == players.length - 1) {
                pointer = 0;
            } else {
                pointer++;
            }
        } else {
            if (players[pointer].getundo().peek() instanceof action) {
                players[pointer].addtoredo(players[pointer].getPosition());// insert the current square of the player in the stack "redo"
                players[pointer].getPosition().removeplayer(players[pointer]);//remove the player from the current position
                players[pointer].getundo().pop();//pop out from the stack the action square and go to the previous square
                players[pointer].moveto((SimpleSquareModel) players[pointer].getundo().pop());
                players[pointer].getPosition().addplayer(players[pointer]);
                if (players[pointer].getmoveX2() > 0 && players[pointer].getmoveX2() < 3) {//if you perform the undo action and the player had moveX2 to the 
                // previous position the he must take it back
                    players[pointer].setmoveX2(players[pointer].getmoveX2() + 1);
                } else {//this case where we have 3 or 0. In both cases we have to set "moveX2" equals to zero
                    players[pointer].setmoveX2(0);
                }
            } else {
                players[pointer].addtoredo(players[pointer].getPosition());
                players[pointer].getPosition().removeplayer(players[pointer]);
                players[pointer].moveto((SimpleSquareModel) players[pointer].getundo().pop());
                players[pointer].getPosition().addplayer(players[pointer]);
                if (players[pointer].getmoveX2() > 0 && players[pointer].getmoveX2() < 3) {
                    players[pointer].setmoveX2(players[pointer].getmoveX2() + 1);
                } else {
                    players[pointer].setmoveX2(0);
                }
            }




        }


        master.setPlayeronActionPanel(players, pointer); //displays the players and who's play next to the "action" panel 
        master.reload(); //this line was written because every time we performed "undo" or "redo" the player disappeared and we
        // must resize the frame in order to be displayed
    }

    /**
     * Postcondition: Restore the player wherever he was before the execution of "undo"
     * tha prokalei tin energia undo sto modelo tou paixnidiou
     * Perform the action "undo" to the model of the game
     */
    public void redo() {
        if (players[pointer].getRedo() == null || players[pointer].getRedo().isEmpty()) {
            master.setontextarea("There is no available moves to repeat for this player ");
        } else {
            players[pointer].getPosition().removeplayer(players[pointer]);
            players[pointer].moveto((SimpleSquareModel) players[pointer].getRedo().pop());
            players[pointer].getPosition().addplayer(players[pointer]);
            if (pointer == players.length - 1) {
                pointer = 0;
            } else {
                pointer++;
            }

        }
        master.setPlayeronActionPanel(players, pointer); 
        master.reload();
    }

    /**
     * Precondition:a!=null 
     * einai akrivos i idia pou iparxei kai stin Board.java kai edo xriazetai
     * logo oti kano anazitisi pinaka ston constructora
     * @param "a" the table to searched
     * @param "key" the key we are looking for in the table
     * @return true if the "key" is in the array, 
     */
    private static boolean isinarray(int[] a, int key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return true;
            }
        }
        return false;
    }
}
