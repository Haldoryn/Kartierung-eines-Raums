package de.dhbw_stuttgart.hb.inf2016.Robot;

import java.net.Socket;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IConnectionIncommingEventHandler;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IDisconnectedEventListener;
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
		DisplayConsole.Init();

		// The escape button on the ev3 robot exits the program.
		while (!Button.ESCAPE.isDown()) {
			initializes();
		}

		DisplayConsole.writeString("Exiting program");
		Delay.msDelay(1000);
	}

	//Make the endpoint static so it can be accessed in the event handles.
	private static RobotEndpoint endpoint = null;

	/**
	 * (Re)initializes the robot protocol and the robot state machine.
	 * 
	 */
	public static void initializes() {

		try {
			LCD.clear();
			DisplayConsole.writeString("Starting Program: Kartierung eines Raumes");
			endpoint = new RobotEndpoint();
			RobotStateMachine stateMachine = new RobotStateMachine(endpoint);
			endpoint.addCommandListener(stateMachine);
			endpoint.addOnConnectionIncommingListener(new IConnectionIncommingEventHandler() {

				@Override
				public void OnNewConnection(Socket sock) {
					DisplayConsole.writeString("Incomming connection from:" + sock.getInetAddress().toString());		
					endpoint.getFromRobotSender().sendReturnMessage("Connection detected");
					DisplayConsole.writeString("Test return message send");
				}
			});
			endpoint.addOnDisconnectListener(new IDisconnectedEventListener() {

				@Override
				public void OnDisconnect() {
					DisplayConsole.writeString("Connection terminated");
				}
			});

			endpoint.open(PORT);
			DisplayConsole.writeString("Endpoint openened on port:" + PORT);
			stateMachine.runStatemachine();
		} catch (Exception e) {

			// Something went wrong.
			DisplayConsole.writeString("An exception occured: " + e.toString());
			while (!Button.ESCAPE.isDown()) {
				Delay.msDelay(1);
			}
		} finally {
			// No matter what happened try to close the robot endpoint, so the port is
			// released and can be reopened.
			if (endpoint != null) {
				endpoint.getFromRobotSender().sendReturnMessage("Closing endpoint");
				endpoint.close();
			}
		}
	}

}
