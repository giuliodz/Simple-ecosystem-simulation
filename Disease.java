import java.util.*;
/**
 * This class describes the charateristics of a disease. A disease can be critical (meaning that an animal will die after
 * 10 days) or non-critical (meaning that the animal will only stay still for 5 days).
 *
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public class Disease
{
     private Random rand;
     //variable that says if the disease is critical or not
     private boolean isCritical;
     // How many days the animal will be affected by this disease.
     private int daysOfIllness;
     //The probability of the disease to be critical.
     private static final double PROBABILITY_OF_BEING_CRITICAL = 0.1;
    /**
     * 
     */
    public Disease()
    {
        rand = new Random ();
        isCritical= false;
        if (rand.nextDouble() <= PROBABILITY_OF_BEING_CRITICAL){
            isCritical = true;
        }
        //If the disease is critical they will keep that illness for 10 days and then die
        if( isCritical == true){
            daysOfIllness = 10;
        }
        //Otherwise they will just have to stay for 5 days.
        else{
            daysOfIllness = 5;
        }
    }
    
    /**
     * Decreases the days the animal will be ill for by one.
     */
    public void decreaseDaysOfIllness()
    {
        daysOfIllness--;
        if (daysOfIllness < 0 ){
            daysOfIllness = 0;
        }

    }
    
    /**
     * @return daysOfIllness The days the animal will be ill for by one.
     */
    public int getDaysOfIllness()
    {
        return daysOfIllness;
    }
    
    /**
     * return isCritical True if the disease is critical (fatal).
     */
    public boolean getIsCritical ()
    {
        return isCritical;
    }

}
