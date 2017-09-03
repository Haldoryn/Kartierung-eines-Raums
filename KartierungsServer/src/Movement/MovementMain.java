package Movement;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.*;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;


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


