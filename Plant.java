import java.util.*;
/**
 * This class describes the charateristics of a plant. It holds for its level of growth, its location,
 * and its disease (if it has any). Plants can also die (for example if they get complitely eaten).
 *
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public class Plant
{
    // Whether the plant is alive or not.
    private boolean alive;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    // The plant's age
    private int growthLevel;
    private static final Random rand = Randomizer.getRandom();
    //The disease of the plant (could be null).
    private Disease disease;

    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location)
    {
       alive = true;
       this.field = field;
       setLocation(location);
       growthLevel = rand.nextInt(5) + 1;
       disease = null; 
    }
    
    /**
     * @retrun disease The disease of the plant.
     */
    public Disease getDisease()
    {
        return disease;
    }
    
    
    /**
     * Sets disease on the plant.
     * 
     * @param disease The disease of the plant (It could be null).
     */
    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }
    
    
    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Increase the growth level by one.
     */
    public void grow()
    {
        growthLevel ++;
    }
    
    /**
     * Decrement Plant growth level by one.
     */
    public void decreaseGrowth()
    {
        growthLevel --;
    }
    
    /**
     * @return growthLevel The level of growth of the plant.
     */
    public int getGrowthLevel()
    {
        return growthLevel;
    }
    
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
       alive = false;

       if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Check whether the plant is alive or not.
     * @return true if the plant is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
}
