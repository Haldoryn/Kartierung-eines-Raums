package de.dhbw_stuttgart.hb.inf2016.Robot;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.RobotEndpoint;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * @author Julian Vogel The main class of the robot program.
 */
public class Main {

	/**
	 * The port used by the robot protocol.
	 * 
	 */
	private final static int PORT = 9876;

	public static void main(String[] args) {
		// The escape button on the ev3 robot exits the program.
		while (!Button.ESCAPE.isDown()) {
			initializes();
		}

		LCD.clear();
		LCD.drawString("Exiting program", 1, 1);
		Delay.msDelay(1000);
	}

	
	/**(Re)initializes the robot protocol and the robot state machine.
	 * 
	 */
	public static void initializes() {
		RobotEndpoint endpoint=null;
		try {
			LCD.clear();
			LCD.drawString("Starting Program: Kartierung eines Raumes", 1, 1);
			endpoint = new RobotEndpoint();
			RobotStateMachine stateMachine = new RobotStateMachine(endpoint.getRobotInterface());
			endpoint.addCommandListener(stateMachine);
			endpoint.open(PORT);
			LCD.drawString("Endpoint openened on port: " + PORT, 1, 1);
			stateMachine.runStatemachine();
		} catch (Exception e) {

			// Something went wrong.
			LCD.clear();
			LCD.drawString("An exception occured" + e, 1, 1);
			while (!Button.ESCAPE.isDown()) {
				Delay.msDelay(1);
			}
		}
		finally
		{	
			//No matter what happened try to close the robot endpoint, so the port is released and can be reopened.
			if(endpoint != null)
			{
				endpoint.close();
			}
		}
	}

}