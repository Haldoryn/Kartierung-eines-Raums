package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;

import java.util.Random;
/**
 * The Controlling class decides, were to go next.
 * @author Samuel Volz
 *
 */
public class Controlling {
    Move NextMove;
    Config config;
    Random random = new Random();

    
    /**
     * Sets the config object. 
     * @param config in an object, that reds from an xml file and returns configured stuff.
     */
    public Controlling(Config config) {
		super();
		this.config = config;
	}

    /**
     * This method decides were to go next.
     * @param doesItFit A boolean that says if the robot fits or not. 
     * @return The next move
     */
	public Move next(boolean doesItFit) {
		
		 if(doesItFit){
			//If the robot can perform a forward move he will perform it.
			NextMove = new Forward((double)config.getConstbyName("distancePerMove"),config);
            return NextMove;
		}
		else{
			//If the robot does not have the space to perform a forward move, he will turn.
			 NextMove = new Turn(random.nextInt(180 * 2) - 180,config);
	            return NextMove;
		}
    }
}
