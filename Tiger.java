import java.util.List;
import java.util.Iterator;
import java.util.Random;

 /**
 * A simple model of a Tiger.
 * Tigers age, move, eat prey, and die. They can have children but only up to a maximum amount.
 * 
 * @author Giulio DiZio and Daniel Idowu
 * @version 21/02/2019
 */
 public class Tiger extends Predator
 {
    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 13;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 60;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.085;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    

    /**
     * Create a Tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(Field field, Location location)
    {
        super(field, location, MAX_AGE);
    }
    
    /**
     * Obtain max age of Tiger.
     * 
     * @return MAX_AGE The age to which a Tiger can live.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Obtain breeding age of Tiger.
     * 
     * @return BREEDIN_AGE The age at which a Tiger can start to breed.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Obtain breeding probability of tiger.
     * 
     * @return BREEDING_PROBABILITY The likelihood of a tiger breeding.
     */
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    
   
    /**
     * Check whether or not this tiger is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born tigers.
     */
    public void giveBirth(List<Animal> newTigers)
    {
        // New tigers are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        //check if the animal can breed and return how many children it wil get.
        int births = breed(BREEDING_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE);
        //Make it breed only if it hasn't reached the maximum number of children yet.
        if(getNumberOfChildren() <= MAX_LITTER_SIZE){
        for(int b = 0; b < births && free.size() > 0; b++) {
             // If parent is infected with critical disease, offspring will also be infected with it. Otherwise not affected.
             if ((getDisease() != null) && getDisease().getIsCritical()){
            Disease disease = getDisease();    
            Location loc = free.remove(0);
            Tiger young = new Tiger(field, loc);
            young.setDisease(disease);
            newTigers.add(young);
            //update the number of its children
            increaseNumberOfChildren();
           } 
           else{
            Location loc = free.remove(0);
            Tiger young = new Tiger(field, loc);
            newTigers.add(young);
            //update the number of its children
            increaseNumberOfChildren();
          }
        }
      }
      }
      
     /**
     * Check if the queried tiger and the one next to it (if there' s any) are of different genders
     * 
     * @return True if the two animals have different genders.
     */
    public boolean differentGenders()
    {
        Field field = getField();
        Location location = getLocation();
        List<Location> adjacent = field.adjacentLocations(location);
        for(Location loc : adjacent) {
            Object object = field.getObjectAt(loc);
            if (object instanceof Tiger) {
                // if it s not a null and it s not a plant then it must be an animal and we need to check if the genders are different
                Animal animal = (Animal) object;
                if (getGender()!= animal.getGender()){
                 //If they are of different genders they will surely mate and if one of the two is infected by a critical disease it will pass it to the other animal.
                   if ((getDisease() != null) && getDisease().getIsCritical()){
                       Disease disease = getDisease();
                       animal.setDisease(disease);
                   }
                return true;
               }
            }
            
        }
        return false;
    }
    
    /**
     * Check if a tiger can breed.
     * 
     * @param breedingAge The age the animal can breed at.
     * @return True if it age is greater or equal to the breedingAge and
     * the tiger next to it is of different gender.
     * 
     */
    public boolean canBreed(int breedingAge)
    {
        return (getAge() >= breedingAge) && differentGenders();
    }
    
    }
        



