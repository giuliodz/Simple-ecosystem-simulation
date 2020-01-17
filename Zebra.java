import java.util.*;
/**
 * A simple model of a zebra.
 * zebras grow, move, breed, and die. They can have children but only up to a maximum amount.
 * 
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public class Zebra extends Prey
{
    // The age at which a zebra can start to breed.
    private static final int BREEDING_AGE = 20;
    // The age to which a zebra can live.
    private static final int MAX_AGE = 75;
    // The likelihood of a zebra breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    private static final Random rand = Randomizer.getRandom();
    


    /**
     * Create a new zebra. A zebra may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the zebra will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Zebra(Field field, Location location)
    {
        super(field, location, MAX_AGE);
    }
    
     /**
     * Obtain max age of zebra.
     * 
     * @return MAX_AGE The age to which a zebra can live.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Obtain breeding age of zebra.
     * 
     * @return BREEDIN_AGE The age at which a zebra can start to breed.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }
    
    /**
     * Obtain breeding probability of zebra.
     * 
     * @return BREEDING_PROBABILITY The likelihood of a zebra breeding.
     */
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    
    /**
     * Check whether or not this zebra is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newZebras A list to add newly born zebras.
     */
    public void giveBirth(List<Animal> newZebras)
    {
        // New zebras are born into adjacent locations.
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
            Zebra young = new Zebra(field, loc);
            young.setDisease(disease);
            newZebras.add(young);
            //update the number of its children
            increaseNumberOfChildren();
           }
           else {
            Location loc = free.remove(0);
            Zebra young = new Zebra(field, loc);
            newZebras.add(young);
            //update the number of its children
            increaseNumberOfChildren();
            }
        }
      }
    }
        
    /**
     * Check if the queried zebra and the one next to it (if there' s any) are of different genders
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
            if (object instanceof Zebra) {
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
     * Check if a zebra can breed.
     * 
     * @param breedingAge The age the animal can breed at.
     * @return True if it age is greater or equal to the breedingAge and
     * the zebra next to it is of different gender.
     * 
     */
    public boolean canBreed(int breedingAge)
    {
        return (getAge() >= breedingAge) && differentGenders();
    }
    
}