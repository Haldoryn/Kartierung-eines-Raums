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
			NextMove = new Forward((int)config.getConstbyName("distancePerMove"),config);
            return NextMove;
		}
		else{
			 NextMove = new Turn(random.nextInt(360 * 2) - 360,config);
	            return NextMove;
		}
    }
}
