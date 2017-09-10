package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;

import java.util.Random;

public class Controlling {
    Move NextMove;
    Config config;

    
    
    public Controlling(Config constants) {
		super();
		this.config = constants;
	}



	public Move next() {
        if(NextMove == null || NextMove instanceof Turn){
        	
        	// if ultrasonicScan < distance + securityDistance
            NextMove = new Forward((int)config.getConstbyName("distancePerMove"),config); // how to set the distance? 
            return NextMove;
        }
        else if(NextMove instanceof Forward){
            Random random = new Random();
            NextMove = new Turn(random.nextInt(360 * 2) - 360,config);
            // return gyro Value
            return NextMove;
        }
        else{
            return null;
        }
    }
}
