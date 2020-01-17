import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing tigers and lions which plays the role of predators ( they are displayed respectively
 * as orange and yellow sqares) and gazelles and zebras which plays the role of preys ( they are 
 * displeyed respectively as blue and black sqares ). There are also plants shown as green rectangules 
 * whose function is to be eaten by preys and make the ecosystem work.
 * 
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    // The probability that a tiger will be created in any given grid position.
    private static final double TIGER_CREATION_PROBABILITY = 0.04;
    // The probability that a lion will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.04;
    // The probability that a gazelle will be created in any given grid position.
    private static final double GAZELLE_CREATION_PROBABILITY = 0.08;
    // The probability that a zebra will be created in any given grid position.
    private static final double ZEBRA_CREATION_PROBABILITY = 0.08;
    // The probability that a plant will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.45;
    // The probability for recreation of plants for every step.
    private static final double PLANT_RECREATION_PROBABILITY = 0.60;
    //The probability that a created plant is infected by a disease.
    private static final double PLANT_DISEASE_PROBABILITY = 0.05;
    // List of animals in the field.
    private List<Animal> animals;
    //List of plants in the field.
    private List<Plant> plants;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The wheather in the simulation.
    private Weather weather;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<>();
        plants = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Tiger.class, Color.ORANGE);
        view.setColor(Lion.class, Color.YELLOW);
        view.setColor(Gazelle.class, Color.BLUE);
        view.setColor(Zebra.class, Color.BLACK);
        view.setColor(Plant.class, Color.GREEN);
    
        weather = new Weather();
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            //delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * animal and plant as well as the weather.
     */
    public void simulateOneStep()
    {
        step++;
        
        // Change weather conditions: day/night, fogginess, rain, temperature.
        weather.changeIsDay();
        weather.changeIsFoggy(weather.generateRandomBoolean());
        weather.changeIsRaining(weather.generateRandomBoolean());
        weather.generateRandomTemperature();
        weather.changeIsExtreme();
        
        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>(); 
       
        // make plants grow , decrease or die.
        for (Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
            Plant plant = it.next();
            
            // plants will grow when it rains and will decrease when no rain is occurring.
            if (weather.getIsRaining()) {
             plant.grow();
            }
           
            else{
                plant.decreaseGrowth();
            }
            
            // plants die when their growth level reaches 0 (ie. it has not had enough rain)
            if(plant.getGrowthLevel() == 0){
                plant.setDead();    
            }
            
            // plants removed from the field when dead
            if(!plant.isAlive()){
                  it.remove();
            }
            
            
        }
        
        //Generate new plants every step forewards, with a maximum of 40 new plants for each step.
        Random rand = new Random();
        double random = rand.nextDouble();
        if (random <= PLANT_RECREATION_PROBABILITY) {
            int numberOfPlants = rand.nextInt(40);
            for (int i = 0; i < numberOfPlants; i++){
                Plant newPlant = new Plant (field, field.getFreeLocation());
                //generate a random double number and if it is less or equal to the probability of a plant to have a disease
                //give to that plant a disease.
                if(rand.nextDouble() <= PLANT_DISEASE_PROBABILITY){
                    Disease disease = new Disease();    
                    newPlant.setDisease(disease);
                 }
                plants.add(newPlant);
            }
        }
        
        //Make all the animals act
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            int MaxAge = 0;
            if (animal instanceof Gazelle) {
                Gazelle anml = (Gazelle) animal;
                MaxAge = anml.getMaxAge();
                anml.act(MaxAge, newAnimals, weather.getIsDay());
            }
            if (animal instanceof Zebra) {
                Zebra anml = (Zebra) animal;
                MaxAge = anml.getMaxAge();
                anml.act(MaxAge, newAnimals, weather.getIsDay());
            }
            if (animal instanceof Tiger) {
                Tiger anml = (Tiger) animal;
                MaxAge = anml.getMaxAge();
                anml.act(MaxAge, newAnimals, weather.getIsDay(), weather.getIsExtreme(), weather.getIsFoggy());
            }
            if (animal instanceof Lion) {
                Lion anml = (Lion) animal;
                MaxAge = anml.getMaxAge();
                anml.act(MaxAge, newAnimals, weather.getIsDay(), weather.getIsExtreme(), weather.getIsFoggy());
            }
            
            // if animal is dead it is removed from the field
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        
        
        // Add the newly born animals to the main lists.
        animals.addAll(newAnimals);
        //update the viewer status.
        view.showStatus(step, field, weather);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field, weather);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                
                // Populate the field with animals and plants in random locations and quantities based on their probabilities.
                if(rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Tiger tiger = new Tiger(field, location);
                    animals.add(tiger);
                }
                else if(rand.nextDouble() <= GAZELLE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Gazelle gazelle = new Gazelle(field, location);
                    animals.add(gazelle);
                }
                else if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lion lion = new Lion(field, location);
                    animals.add(lion);
                }
                else if(rand.nextDouble() <= ZEBRA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zebra zebra = new Zebra(field, location);
                    animals.add(zebra);
                }
                else if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Plant plant = new Plant(field, location);
                    //generate a random double number and if it is less or equal to the probability of a plant to have a disease
                    //give to that plant a disease.
                    if(rand.nextDouble() <= PLANT_DISEASE_PROBABILITY){
                      Disease disease = new Disease();    
                      plant.setDisease(disease);
                    }
                    plants.add(plant);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
