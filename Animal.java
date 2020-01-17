import java.util.*;
import java.util.Random;
/**
 * A class representing shared characteristics of all the animals present in the 
 * simulation.
 * 
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age
    private int age;
    //The gender of the animal. If 0 then gender is male; if 1 then gender is female.
    private int gender;
    //Number of children the predator has given to birth.
    private int numberOfChildren;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The disease the animal is infected by. It could be null (reppresenting that it's not infected by any).
    private Disease disease;
    
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param maxAge The maximum age it can reach.
     */
    public Animal(Field field, Location location, int maxAge)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        gender = rand.nextInt(2);
        age = rand.nextInt(maxAge);
        numberOfChildren = 0;
        disease = null;       
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * This methos is left empty to make the compailer know there is a method called act
     * for every animal, then it will be overridden (implemented) in the predator and prey class. 
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Animal> newAnimals)
    {
        
    }
    
    /**
     * Method to get the number of children an animal has already given birth to.
     */
    public int getNumberOfChildren()
    {
        return numberOfChildren;
    }
    
    /**
     * Increase the number of children by one.
     */
    public void increaseNumberOfChildren()
    {
        numberOfChildren++;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Give an animal a disease.
     * @param disease The diseas to give to the animal.
     */
    public void setDisease(Disease disease)
    {
        this.disease = disease;
        
    }
    
    /**
     * @return The disease of the animal
     */
    public Disease getDisease()
    {
        return disease;
    }
    
    /**
     * Get the age of the animal
     * @return age The age of the animal.
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * increment the animals age by one year up to it's maximum age.
     * The animal dies when it gets older than its maximum age
     * 
     * @param max age
     */
    protected void incrementAge(int maxAge)
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
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
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
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
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
   
    
    /**
     * To obtain the gender of animal for breeding purposes
     */
    public int getGender()
    {
        return gender;
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
     public int breed(int breedingAge, double breedingProbability, int maxLitterSize)
    {
        int births = 0;
        if(canBreed(breedingAge) && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }
    
    /**
     * Abstract method to give birth to new animals. Implemented in each of the specifc animal specie subclass.
     * @param newRabbits A list to return newly born animals.
     */
    public abstract void giveBirth(List<Animal> newAnimal);
    
    /**
     * Abstract method for whether the animal is able to breed or not. Implemented in each of the specifc animal 
     * specie subclass.
     * @param breedingAge The age the animal can breed.
     * @return true if it can breed.
     */
    public abstract boolean canBreed(int breedingAge);
    
    
}
