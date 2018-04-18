package snakesandladders;

import java.util.LinkedList;

/**
 * @author Mihalis Chaviaras
 * This interface exists to inform another class. In this case is implemented 
 * by the "SimpleSquareModel". Whenever the method action is called the "SquareRenderer"
 * is informed
 *
 */
public interface Subject {
        
     /**
      *
      * It informs the listeners by calling for each one the method modelchanged()
      */
     public void update();
}
