package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Movement;


/**
 * @author Jan Wäckers
 *
 */
public class MovementMain {
	
	public static void main(String args[]){
	
		RandomMovement moving = new RandomMovement();
		try {
			moving.move();
		} catch ( Exception e) {
			
			e.printStackTrace();
			System.err.println("An Error ocurred");
		} 
	}
	
}


