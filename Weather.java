import java.util.*;
/**
 * Class that stores the details and conditions for weather in the field.
 * It includes rain, temperature and fog.
 * @author Giulio Di Zio and Daniel Idowu
 * @version 21/02/2019
 */
public class Weather
{
    // time of day
    private boolean isDay;
    // if it is raining or not
    private boolean isRaining;
    // if it is foggy or not
    private boolean isFoggy;
    // if it is above 40 degrees or not
    private boolean extremeTemperature;
    // the temperature in degrees
    private int temperature;
    private Random rand;
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        isDay = true;
        isRaining = generateRandomBoolean();
        isFoggy = generateRandomBoolean();
        extremeTemperature= generateRandomBoolean();
        rand = new Random();
        temperature = rand.nextInt(51);
    }
    
    /**
     * @retun a string which describes the current state of the weather.
     */
    public String toString()
    {
        String timeOfDay;
        String weatherConditions = "";
        if(isDay) {
            timeOfDay = "Day";
        }
        else {
            timeOfDay = "Night";
        }
        
        if(isRaining){
            weatherConditions += "Raining, ";
        }
        else if(isFoggy) {
            weatherConditions += "Foggy, ";
        }
        weatherConditions += (temperature + "C.");
        return "Time Of Day: " + timeOfDay + ", " + "Weather conditions: " + weatherConditions;
    }
    
    /**
     * @return a random boolean response (either true or false).
     */
    public boolean generateRandomBoolean()
    {
        Random rand = new Random();
        int random = rand.nextInt(2);
        if (random == 1){
            return true;
        }
        else{
            return false;   
        }
    }
    
    /**
     * @return a random temperature from 0 to 50 degrees.
     */
    public void generateRandomTemperature()
    {
        int randomT = rand.nextInt(51);
        temperature = randomT;
    }

    /**
     * @return    True if it's day.
     */
    public boolean getIsDay()
    {
        return isDay;
    }
    
    /**
     * Simple method for changing the time of day
     */
    public void changeIsDay()
    {
        isDay = !isDay;
    }
    
    /**
     * @return    True if it's foggy.
     */
    public boolean getIsFoggy()
    {
        return isFoggy;
    }
    
    /**
     * Simple method for setting the weather to foggy
     */
    public void changeIsFoggy(boolean random)
    {
        isFoggy = random;
    }
    
    /**
     * @return    True if it's raining 
     */
    public boolean getIsRaining()
    {
        return isRaining;
    }
    
    /**
     * Simple method for setting the weather to raining
     */
    
    public void changeIsRaining(boolean random)
    {
        isRaining = random;
    }
    
    /**
     * @return    True if weather is over 40 degrees.
     */
    public boolean getIsExtreme()
    {
        return extremeTemperature;
    }
    
    /**
     * Simple method for setting the weather to extreme conditions.
     */
    public void changeIsExtreme()
    {
        if (temperature <= 40) {
            extremeTemperature = true;
        }
        else {
            extremeTemperature = false;
        }
    }
    
    /**
     * @return   the temperature in the field.
     */
    public int getTemperature()
    {
        return temperature;
    }
    
    /**
     * Change the temperature of the field.
     * @param foeld's temperature
     */
    public void changeTemperature(int temperature)
    {
        this.temperature = temperature;
    }
    
}
