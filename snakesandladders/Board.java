package snakesandladders;

import java.util.LinkedList;
import java.util.Random;

/**
 * This class maintain the mesh of the game which consists of all the squares
 * either action or not.
 * Contains an array with elements of type "SquareRenderer" which contain 
 * references to elements of type "simplesquaremodel"
 *  
 */
public class Board {

    private SquareRenderer[] grid;
    int[] positions;//the positions of all the action square
    int[] moveto;//shows where the snakes or the ladder send the player 
    String[] actions;//shows which of the action square is this 
    private int row; //how many rows the mesh has
    private int column;//how many columns the mesh has
    public static int rows;
    public static int columns;
    /**
     * Preconditions: "row","column" >0
     * 0<=action_sq<=row*column, 0<=ladds_perc,snak_perc<=1
     * @param "row" how many rows the mesh has
     * @param "ladds_perc" how many percent are ladders
     * @param "snak_perc" how many percent are snakes
     * @param "moveX2_perc" how many percent are moveX2
     * @param "randomadjustment_perc" how many percent are 'random adjustment'
     *
     */
    public Board(int row, int column, int ladds_perc, int snak_perc, int moveX2_perc, int randomadjustment_perc) {
        //it must make one by one the squares from 1 to "row"*"column" and place them to array grid
        this.row=row;
        this.column=column;
        rows=row;
        columns=column;
        stratigiki_gia_to_plegma(row, column, ladds_perc, snak_perc,randomadjustment_perc,  moveX2_perc);
        grid = new SquareRenderer[row * column];
        for (int i = 0; i < positions.length; i++) {
            if (actions[i].equals("ladder") || actions[i].equals("snake")) {
                action lad = new action(positions[i], null, moveto[i]);
                grid[positions[i] - 1] = new SquareRenderer(lad);
                grid[positions[i] - 1].getbox().setListener(grid[positions[i] - 1]);
            } else if (actions[i].equals("randomadjustment")) {
                randomadjustment rnd = new randomadjustment(positions[i], null);
                grid[positions[i] - 1] = new SquareRenderer(rnd);
                grid[positions[i] - 1].getbox().setListener(grid[positions[i] - 1]);
            } else if (actions[i].equals("moveX2")) {
                moveX2 mv = new moveX2(positions[i], null);
                grid[positions[i] - 1] = new SquareRenderer(mv);
                grid[positions[i] - 1].getbox().setListener(grid[positions[i] - 1]);
            }
        }
        for (int i = 0; i < row * column; i++) {
            if (!isinarray(positions, i + 1)) { //if it is not action
                SimpleSquareModel tempsquare = new SimpleSquareModel(i+1, null);
                grid[i] = new SquareRenderer(tempsquare);
                grid[i].getbox().setListener(grid[i]);//we put as listener the SquareRenderer
            }
        }
        for(int i=1;i<=rows*columns;i++){
            getsquare(i).getbox().addboard(this);
        }
    }
    /**
     * 
     * @return the number of rows
     */
    public int getrows(){
        return row;
    }
    /**
     *
     * @return the number of columns
     */
    public int getcolumns(){
        return column;
    }
    /**
     *
     * @return the number of the squares
     */
    public int getsumofsquares(){
        return  grid.length;
    }

    /**
     * Preconditions : 1<=n<=grid.length
     * @param n to kouti me ton arithmo n
     * @return The "SquareRenderer" of the square with the number "n"
     */
    public SquareRenderer getsquare(int n) {
        return grid[n - 1];
    }

    /**
     *
     * precondition: ladders+snakes+ran_adj+moveX2<=100 ,rows*columns>0
     * postcondition: It assigns the action squares to random  positions and checks that there is no action panel 
     * that sends you to another action panel (snake to ladder or reverse or snake to snake or ladder to ladder)
     * @param "rows" the number of rows 
     * @param "columns" the number of columns
     * @param "ladders"  how many percent are ladders
     * @param "snakes" how many percent are ladders
     * @param "ran_adj" how many percent are 'random adjustment'
     * @param "moveX2" how many percent are 'moveX2'
     */
    private void stratigiki_gia_to_plegma(int rows, int columns, int ladders, int snakes, int ran_adj, int moveX2) {
	//firstly we must see where the action squares will be placed and after that where the 
	// ladders and the snakes redirect the players
        int length_ladders = (int) ((ladders / 100.0) * (rows * columns));
        int length_snakes = (int) ((snakes / 100.0) * (rows * columns));
        int length_ran_adj = (int) ((ran_adj / 100.0) * (rows * columns));
        int length_moveX2 = (int) ((moveX2 / 100.0) * (rows * columns));
        int length_sum = length_ladders + length_snakes + length_ran_adj + length_moveX2;
        actions = new String[length_sum];
        positions = new int[length_sum];
        moveto = new int[length_ladders + length_snakes];
        int count_position = 0;
        int count_length_sum = 0;
        int count_moveto = 0;
        Random pouthaeinaitokouti = new Random();
        Random tikoutithaeinai = new Random();

        while (count_length_sum <= length_sum - 1) {


            int pou = (pouthaeinaitokouti.nextInt(rows * columns - 4) + 3);
//          the above is an integer random number in the interval [3 , (rows*columns-2)], notice that the first 
//	    and the last square can't be action
            if (!isinarray(positions, pou) && !isinarray(moveto, pou)) {//we must check if this square has been assigned 
            //to another action and if redirects you to another snake or ladder  

		//below we split the interval 0,length_sum to smaller intervals 
		//in order to know which action square will be placed
                if (count_length_sum <= length_ladders - 1) {//we will place ladder action square
                    positions[count_position] = pou;
                    actions[count_position] = "ladder";
                    count_position++;
                    Random pouthapigainei = new Random();
                    int move_to = pouthapigainei.nextInt(rows * columns - pou - 1) + pou + 1;//produce a number between
                    //the the number of the next square and the previous of the finish

                    while (isinarray(positions, move_to)) {    //den tha prepei na se stelnei se kapoio allo tetragono drasis
                        move_to = pouthapigainei.nextInt(rows * columns - pou - 1) + pou + 1;
                    }
                    moveto[count_moveto] = move_to;
                    count_moveto++;


                } else if (count_length_sum >= length_ladders && count_length_sum <= length_ladders + length_snakes - 1) {
		    //we will place snake action square
                    positions[count_position] = pou;
                    actions[count_position] = "snake";
                    count_position++;
                    Random pouthapigainei = new Random();
                    int move_to = pouthapigainei.nextInt(pou - 2) + 2;//it produces a random number between 2 and the previous
                    // square of this which is a snake

                    while (isinarray(positions, move_to)) {//it must not redirect the player to another action square
                        move_to = pouthapigainei.nextInt(pou - 2) + 2;
                    }
                    moveto[count_moveto] = move_to;
                    count_moveto++;


                } else if (count_length_sum >= (length_ladders + length_snakes) && count_length_sum <= (length_ladders + length_snakes - 1 + length_ran_adj)) {
		    //we will place a 'random adjustment' square
                    positions[count_position] = pou;
                    actions[count_position] = "randomadjustment";
                    count_position++;
                } else if (count_length_sum >= (length_ladders + length_snakes + length_ran_adj) && count_length_sum <= length_sum - 1) {
		    //we will place a moveX2 action square
                    positions[count_position] = pou;
                    actions[count_position] = "moveX2";
                    count_position++;
                }



            } else {


                while (isinarray(positions, pou) || isinarray(moveto, pou)) {
                    pou = (pouthaeinaitokouti.nextInt(rows * columns - 2) + 2);
                }
                if (count_length_sum <= length_ladders - 1) {
                    positions[count_position] = pou;
                    actions[count_position] = "ladder";
                    count_position++;
                    Random pouthapigainei = new Random();
                    int move_to = pouthapigainei.nextInt(rows * columns - pou - 1) + pou + 1;

                    while (isinarray(positions, move_to)) {  
                        move_to = pouthapigainei.nextInt(rows * columns - pou - 1) + pou + 1;
                    }
                    moveto[count_moveto] = move_to;
                    count_moveto++;


                } else if (count_length_sum >= length_ladders && count_length_sum <= length_ladders + length_snakes - 1) {
                    positions[count_position] = pou;
                    actions[count_position] = "snake";
                    count_position++;
                    Random pouthapigainei = new Random();
                    int move_to = pouthapigainei.nextInt(pou - 2) + 2;
                    while (isinarray(positions, move_to)) {    
                        move_to = pouthapigainei.nextInt(pou - 2) + 2;
                    }
                    moveto[count_moveto] = move_to;
                    count_moveto++;


                } else if (count_length_sum >= (length_ladders + length_snakes) && count_length_sum <= (length_ladders + length_snakes - 1 + length_ran_adj)) {
                    positions[count_position] = pou;
                    actions[count_position] = "randomadjustment";
                    count_position++;
                } else if (count_length_sum >= (length_ladders + length_snakes + length_ran_adj) && count_length_sum <= length_sum - 1) {
                    positions[count_position] = pou;
                    actions[count_position] = "moveX2";
                    count_position++;
                }



            }
            count_length_sum++;
        }
    }

    /**
     * It is an auxiliary method to search in an array
     * @param "a" the array through which we will search
     * @param "key" the key that we are looking for in the array
     * @return true if the key exists to the array, false otherwise
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
