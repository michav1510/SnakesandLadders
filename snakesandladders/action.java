package snakesandladders;

import java.util.LinkedList;

/**
 *  @author Mihalis Chaviaras
 * With this class the squares are distinguished, the squares which are 
 * ladders and the others.
 */
public class action extends SimpleSquareModel {

    private int where;//in which square the player must be redirected

    /**
     * Precondition: 1<=numberofsquare<=rows*columns
     * @param "numberofsquare" it is the position of the square 
     * @param "players" we must know how much players are on the square 
     */
    public action(int numberofsquare, Player []players, int n) {
        super(numberofsquare, players);
        where = n;
    }

    /**
     * @return to which square the player must be redirected to 
     */
    public int get_where() {
        return where;
    }

   
    /**
     *
     * Precondition: p!=null
     * The @Override exists due to the different action this kind of squares 
     * performs on the players
     *
     * @param "p" the player over whom the action is performed
     */
    @Override
    public void action(Player p) {
        p.getPosition().removeplayer(p);
        p.addHistory(p.getPosition());//vazoume tin thesi sto history
        p.moveto(this.getboard().getsquare(where).getbox());
        p.getPosition().addplayer(p);
    }
}
