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
 * First Implementation of the movement algorithm
 * What it should do: Let the robo move until there is an obstacle (less than distance of 25). Then the robo  rotates and mesure again. 
 * If no obstacle is recognized, it moves again.
 */
public class MovementMain {
	
	public static void main(String args[]){
		
	}
	// control booleans
	// UltrasonicValue frontMeasuring= new UltrasonicValue();
	private int frontMeasuring; 
	private boolean newUltrasonicValue = false;
	private boolean movefinished = false;
	
	
	

	public  void move() throws InstantiationException, IllegalAccessException {
		System.out.println("Starting ProtocolConsole...");

		 Scanner in = new Scanner(System.in);

		// Create a endpoint and connect it to the robot.
		ControlerEndpoint endpoint = new ControlerEndpoint();
		endpoint.addCommandListener(new ICommandReceiver() {

			@Override // if event is received, the Control Booleans above are setted
			public void commandReceived(CommandBase cmd) {
				CommandType type = cmd.getType();
				System.out.println("Response from robot: " + cmd.toString());
				
				switch( type){
				case returnUltrasonic :
					frontMeasuring = Integer.parseInt(cmd.toString());
					newUltrasonicValue = true; 
					break;
				case returnMotor:
					movefinished = true;
					break;
				}
			
			}
		});
		try {
			System.out.println("Trying to connect to the robot");
			endpoint.connect(InetAddress.getByName("10.0.1.1"), 9876);
			System.out.println("Connection successfully estabished");
		} catch (Exception e) {
			System.err.println("Could not connect to to robot: " + e);
		}
		IControler controler = endpoint.getControlerInterface();
		
		while(true){
			Random gen =new Random();
			int rand;
			if(movefinished == true){ // check if robo is moving
			controler.sendGetUltrasonic();
			newUltrasonicValue = false; // signal: waiting for Value
			if(newUltrasonicValue == true){ // new Value Received?
				if(frontMeasuring > 5 && frontMeasuring < 80 ){ // checks if value is ok
					if(frontMeasuring > 25){ // checks if distance is enough
						controler.sendMoveMotor(180, 180, 360, 360);
						movefinished = false; // Signal: waiting for Movement
					} 
					else {
						rand = gen.nextInt(360);
						controler.sendMoveMotor(180, 0, rand*8, 0); // rotate random degrees. I figured out that with one wheel moved: 
																	// gewünschter winkel *8 = Gesamtwinkel für Motorbewegung
						movefinished = false;
						
					}
					
				}
				else System.out.println("Ultrasonic Value is not suitable and gets ignored");
			}
		  }
		} // end while

	}
}


