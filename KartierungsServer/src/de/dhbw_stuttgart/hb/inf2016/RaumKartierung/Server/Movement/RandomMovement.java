package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Movement;

import java.net.InetAddress;
import java.util.Random;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ControlerEndpoint;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ICommandReceiver;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IControler;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandType;

/**
 * @author Jan Wäckers
 *  First Implementation of the movement algorithm
 * What it should do: Let the robo move until there is an obstacle (less than distance of 25). Then the robo  rotates and measure again. 
 * If no obstacle is recognized, it moves again.
 */
public class RandomMovement {

	// control booleans
		// UltrasonicValue frontMeasuring= new UltrasonicValue();
		private int frontMeasuring;
		private int rand;
		private Random gen =new Random();
		/* old:
		private boolean newUltrasonicValue = false;
		private boolean movefinished = true;
		*/
		
		

		public  void move() throws InstantiationException, IllegalAccessException, InterruptedException{
			System.out.println("Starting ProtocolConsole...");

			 //Scanner in = new Scanner(System.in);

			// Create an endpoint and connect it to the robot.
			ControlerEndpoint endpoint = new ControlerEndpoint();
			endpoint.addCommandListener(new ICommandReceiver() {

				@Override // if event is received, the Control Booleans above are setted
				public void commandReceived(CommandBase cmd) {
					CommandType type = cmd.getType();
					System.out.println("Response from robot: " + cmd.toString());
					
					switch( type){
					case returnUltrasonic :
						frontMeasuring = Integer.parseInt(cmd.toString());
						// old:  newUltrasonicValue = true; 
						break;
				/* old:	case returnMotor:
						movefinished = true;
						break;
						*/
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
			
			/* moving algorithm old version 
			while(rand !=0){
				
				
				
				if(movefinished == true){ // check if robo is moving
				newUltrasonicValue = false;  // signal: waiting for Value
				controler.sendGetUltrasonic();
			
				System.out.println("Waiting Sucessfull");
				while(newUltrasonicValue == false){ // waiting for value
					//wait
				}
					if(frontMeasuring > 5 && frontMeasuring < 80 ){ // checks if value is usable
						if(frontMeasuring > 25){ // checks if distance is enough
							movefinished = false; // Signal: waiting for Movement
							controler.sendMoveMotor(180, 180, 360, 360);
						} 
						else { // rotate randomly
							movefinished = false;
							rand = gen.nextInt(360);
							controler.sendMoveMotor(180, 0, rand*8, 0); // rotate random degrees. I figured out that with one wheel moved: 
																		// gewünschter winkel *8 = Gesamtwinkel für Motorbewegung
							
							
						}
						
					}
					else System.out.println("Ultrasonic Value is not suitable and gets ignored");
				
			  }
			} //end while
			 */
			
			// new Moving algorithm
			while(true){
				
				controler.sendGetUltrasonicAndWait(2000);
				if(frontMeasuring > 5 && frontMeasuring < 80 ){ // checks if value is usable
					if(frontMeasuring > 25){ // checks if distance is enough
						 
						controler.sendMoveMotorAndWait(180, 180, 360, 360, 5000);
						// Move Forward
					} 
					else { // rotate randomly
						
						rand = gen.nextInt(360);
						//TURN
						controler.sendMoveMotorAndWait(180, 0, rand*8, 0, 5000);
					// rotate random degrees. I figured out that with one wheel moved: 
				   // gewünschter winkel *8 = Gesamtwinkel für Motorbewegung	
					}
				}
				else System.out.println("Ultrasonic Value is not suitable and gets ignored");
			} // end while

		}
}
