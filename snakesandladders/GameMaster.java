package snakesandladders;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *  @author Michalis Chaviaras
 * This class will help us to link graphics with the model of the game
 * and specifically with the snakksandladds
 */
public class GameMaster {

    private SnaksandLadds gameshell;
    private gameGUI gui;
    private Board board;
    private String names[];

    public static void main(String[] args) {
        GameMaster master = new GameMaster();
        master.start();
    }

    /**
     * This method starts the game, read the file "game.properties" and initialize 
     * the graphics and the board which is needed for the graphics.
     */
    public void start() {
        Properties prop;
        prop = ReadProperties();
        board = new Board(new Integer(prop.getProperty("game_rows")).intValue(), new Integer(prop.getProperty("game_columns")).intValue(),
                new Integer(prop.getProperty("ladder")).intValue(), new Integer(prop.getProperty("snake")).intValue(),
                new Integer(prop.getProperty("moveX2")).intValue(), new Integer(prop.getProperty("randomadjustment")).intValue());

        gui = new gameGUI(board, this);



    }

    /**
     * Precondition:the table names[] must not be null 
     * Postcondition: initialize the SnakksandLadds
     * @param names the table with the names
     */
    public void continuethegame(String names[]) {
        this.names = names;
        if (names == null) {
            System.out.println("den iparxoun onomata");
        } else {
            gameshell = new SnaksandLadds(this.names, board, this);
        }
    }

    /**
     * Precondition:players!=null , 0<=pointer<=players.length-1
     * Postcondition: displays the players "players" and shows who plays next 
     * to the action panel through the method setnamesonactionpanel which exists
     * in gameGUI
     * @param players the table with the players which will be displayed 
     * @param pointer the integer corresponds to  the position of the table, of the player
     * whom plays.
     *
     */
    public void setPlayeronActionPanel(Player[] players, int pointer) {
        gui.setnamesonactionpanel(players, pointer);
    }

    /**
     * This method executed from the method "actionperformed" in the "gameGUI".
     *  With this method the "play" of the "SnakksandLadds" is executed. 
     */
    public void play() {
        gameshell.play();
    }

    /**
     * This method executed from the method "actionperformed" in the "gameGUI".
     *  With this method the "undo" of the "SnakksandLadds" is executed. 
     */
    public void undo() {
        gameshell.undo();
    }

    /**
     * This method executed from the method "actionperformed" in the "gameGUI".
     *  With this method the "redo" of the "SnakksandLadds" is executed. 
     */
    public void redo() {
        gameshell.redo();
    }

    /**
     * Precondition: 1<=zaria<=12
     * This method calls the corresponding method of "gameGUI"
     * @param "zaria" What is the number the dice brought?
     */
    public void diksetinzariapouirthe(int zaria) {
        gui.diksetinzaria(zaria);
    }

    /**
     * Precondition:p!=null
     * Through this method the "winner" method of "gameGui" is executed 
     * and there is a pop up window to show who is the winner.
     */
    public void winner(Player p) {
        gui.winner(p);
    }

    /**
     * I use it because every time the "undo" was executed in order to display
     * the "player" I had to resize my frame
     */
    public void reload() {
        gui.validate();
    }

    /**
     * In order to send message the "snaksandladds" with the GUI this method is used
     * @param "message" it is the message we want to be displayed in  the LogPanel
     */
    public void setontextarea(String message) {
        gui.writeonlogpanel(message);
    }

    /**
     * It must be private because the "game.properties" is read only here  
     * @return an object of type properties which contains all the required informations
     * about the boxes, how much are they and what type are they.
     */
    private static Properties ReadProperties() {
        try {
//         a counter for the while
            int counter = 0;

            FileInputStream fstream = new FileInputStream("snakesandladders/game.properties");


//          I will put the things I will read in an object of type "Properties"
            Properties prop = new Properties();
//            prop.load(stream);


            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
//           reads the file line by line
            counter = 1;
            while ((strLine = br.readLine()) != null) {

//              prints what is it read from the screen
                if (counter >= 2 && counter <= 7) {//an einai anamesa stin
//                    deuteri grammi kai tin evdomi

                    int counter1 = 1;//it counts how much number we have
//                  starts from 1 because we want to read a single number

                    StringTokenizer str = new StringTokenizer(strLine);
                    while (str.hasMoreTokens()) {
                        String temp = str.nextToken();

//		it shows if all the characters are numbers
                        int counter2 = 0;
                        for (int i = 0; i < temp.length(); i++) {//diatrexo to string

                            if (Character.isDigit(temp.charAt(i))) {
                                counter2++;
                            }

                        }
                        if (counter2 == temp.length()) {//it is a number
                            if (counter1 == 1) {// then the number must be imported
                                if (counter == 2) {
                                    prop.setProperty("game_rows", temp);
                                } else if (counter == 3) {
                                    prop.setProperty("game_columns", temp);
                                } else if (counter == 4) {
                                    prop.setProperty("ladder", temp);
                                } else if (counter == 5) {
                                    prop.setProperty("snake", temp);
                                } else if (counter == 6) {
                                    prop.setProperty("moveX2", temp);
                                } else if (counter == 7) {
                                    prop.setProperty("randomadjustment", temp);
                                }
                            }
                        }

                    }
                }
                counter++; //the counter is increased to go to the next line

            }
            //Close the input stream
            in.close();

            return prop;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
