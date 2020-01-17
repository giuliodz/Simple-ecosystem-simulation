import java.util.*;
import java.util.Random;
/**
 * Class of Prey.
 * Prey eat plants, grow, breed and die.
 * They are also eaten by predators
 * 
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public abstract class Prey extends Animal
{
    // The level of energy a prey has. If it reaches 0 it dies.
    private int foodLevel;
    // a random variable to randomise certain conditions
    private Random rand;
    // the food value that plants provide to prey when they get eaten
    private static final int PLANT_FOOD_VALUE = 20;

    /**
     * Constructor for objects of class Prey
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param maxAge The maximum age it can reach.
     */
     public Prey(Field field, Location location, int maxAge)
     {
        super(field, location, maxAge);
        rand = new Random();
        foodLevel = rand.nextInt(PLANT_FOOD_VALUE) + 1;
    }
    
    /**
     * Look for plants adjacent to the current location the animal is at.
     * Only the first live plant is eaten.
     * If the plant was infected the prey wil get its same disease.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object plant = field.getObjectAt(where);
            //If the object next to it is a plant the prey is going to eat part of that.
            if(plant instanceof Plant) {
             Plant plt = (Plant) plant;
                if (!(plt.getGrowthLevel() == 0)){
                // eat the plant and then move to a free adjacent location to it.
                foodLevel = PLANT_FOOD_VALUE;
                plt.decreaseGrowth();
                if (plt.getDisease() != null) {
                    setDisease(plt.getDisease());
                }
                //return the position of the eaten plant.
                return getField().freeAdjacentLocation(where);
             }
            }
        }
        return null;
    }
    
    /***
     * Make the prey act. It will become older and be more hungry (decreasing its food level).
     * It will also be able to eat plants, move, and procreate but only under certain conditions.
     * It might also be the case that a prey has to die (for example if it got too old).
     * 
     * @param maxAge The maximum age the prey can reach.
     * @param newPrey A list of newly born preys.
     * @param isDay True if it's day.
     */
    public void act(int maxAge, List<Animal> newPrey, boolean isDay)
    {
        
        incrementAge(maxAge);
        incrementHunger();
        //Make prays move only during the day
        if(isAlive() && !isDay) {
            //if the animal has got a non critical disease it cannot move.
            if((getDisease() != null)  && !getDisease().getIsCritical()){
                getDisease().decreaseDaysOfIllness();
                if(getDisease().getDaysOfIllness() == 0){
                    // if the disease has ended set it to null.
                    setDisease(null);
                }
                return;
            }
            //if the animal has a critical disease and it has had it for 10 days it must die.
            else if((getDisease() != null) && getDisease().getIsCritical()){
                getDisease().decreaseDaysOfIllness();
                if(getDisease().getDaysOfIllness() == 0){
                    setDead();
                    return;
                }
            }
            giveBirth(newPrey);            
            // Move towards a source of food if found and eat it.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // Food (plant) found. Eat it and then move to a free adjacent field if there's any... Otherwise stay.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        
      }
   
    }
    
     /**
     * Make this prey more hungry. This could result in the prey's death.
     */
    protected void incrementHunger()
    {
       foodLevel--;
       if(foodLevel <= 0) {
            setDead();
        }
    }
    }

   