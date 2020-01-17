import java.util.*;
/**
 * Predators include Lions and Tigers. They hunt other animals for food and require this food to survive. 
 * They eat Prey (Gazelles and Zebras). All the functions that predators share are implemented in this class.
 *
 * @author Daniel Idowu and Giulio Di Zio
 * @version 21/02/2019
 */
public abstract class Predator extends Animal
{
    // The Predator's food level, which is increased by eating prey.
    private int foodLevel;
    private Random rand;
    // The value of which eating prey replenishes the predator's hunger (foodLevel).
    private static final int PREY_FOOD_VALUE = 28;

    /**
     * Constructor for objects of class Predator. Sets the location and randomises the food level of the predators as they are created.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param maxAge The maximum age it can reach.
     */
    public Predator(Field field, Location location, int maxAge)
    {
        super(field, location, maxAge);
        rand = new Random();
        foodLevel = rand.nextInt(PREY_FOOD_VALUE);
    }
    
    
    /**
     * Obtain the food value of preys.
     * 
     * @return PREY_FOOD_VALUE The food value of a prey.
     */
    public int getpreyFoodValue()
    {
        return PREY_FOOD_VALUE;
    }
    
    /**
     * Look for prey adjacent to the current location.
     * Only the first live prey is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            //If the object next to its location is a prey the predator eats it.
            if(animal instanceof Prey) {
                Prey prey = (Prey) animal;
                if(prey.isAlive()) { 
                    // set the prey to dead and make the predator less hungry.
                    prey.setDead();
                    foodLevel = PREY_FOOD_VALUE;
                    if (prey.getDisease() != null) {
                    //If the eaten prey had a disease, the predator is goin to eat its same disease.
                    setDisease(prey.getDisease());
                  }
                    return where;
                }
            }
        }
        return null;
    }
    
    
     
    /***
     * Implements the behaviour of the predators. Predators get older, become more hungry, can hunt and procreate.
     * Predators can move both during night and day, but they can hunt only if the temperature is not extreme (over 
     * 40 C), it is not foggy and if they are hungry (their food level is lower or equal to 20).
     * 
     */
    public void act(int maxAge, List<Animal> newPredators, boolean isDay, boolean isExtreme, boolean isFoggy)
    {
        incrementAge(maxAge);
        incrementHunger();
        if(isAlive()) {
            //if the animal has got a non critical disease it doesn't move.
            if((getDisease() != null) && !getDisease().getIsCritical()){
                getDisease().decreaseDaysOfIllness();
                if(getDisease().getDaysOfIllness() == 0){
                    setDisease(null);
                }
                return;
            }
            //if the animal has a critical disease and it has had it for 10 days it must die.
            else if((getDisease() != null) && getDisease().getIsCritical()){
                getDisease().decreaseDaysOfIllness();
                if(getDisease().getDaysOfIllness() == 0){
                    //If the "deadline" of the critical disease has arrived set the animol to dead.
                    setDead();
                    return;
                }
            }
            giveBirth(newPredators);            
            // Move towards a source of food if found.
            if(isDay && !isExtreme && !isFoggy && (foodLevel<= 20)){
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        
        }
        //If the animal is full make it move to a random adjacent location.
        else if (foodLevel > 20){
            Location newLocation = getField().freeAdjacentLocation(getLocation());
               if(newLocation != null) {
                 setLocation(newLocation);
          }
        }
      }
        
   
    }
    


     /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    protected void incrementHunger()
    {
       foodLevel--;
       if(foodLevel <= 0) {
            setDead();
        }
    }

}
