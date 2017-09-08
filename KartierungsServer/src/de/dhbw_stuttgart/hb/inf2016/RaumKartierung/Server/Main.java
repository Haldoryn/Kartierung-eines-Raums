package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Constants.Constants;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Controlling;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.MainWindow;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.omg.PortableServer.ServantActivator;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.*;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandType;
/**
 * 
 * @author Samuel Volz
 *
 */

public class Main {

	private static RobotInteractionHandler robotInteractionHandler;
	private static Constants constants = new Constants("temp");
	private static boolean allowRobotToMove;
	private static boolean isRunning;
	/**
	 * main is the starting class of this program. It calls the GUI and goes thru the procedure of the robot.
	 * @param args just some args idk
	 */
	public static void main(String[] args) {
		/*
		 * first up the main needs to start the GUI. 
		 * It has to wait till the GUI responds with informations on how to proceed. 
		 * If the start button was pressed, the main continues with the procedure.
		 */
		MainWindow window = new MainWindow();
		window.Show();
	}
	/**
	 * The start method gets called, if the start button in the GUI gets clicked.
	 * It starts the interaction procedure.
	 */
	public static void start() {
		if(isRunning == false) {
			isRunning = true;
			try {
				robotInteractionHandler = new RobotInteractionHandler();
			} catch (InstantiationException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			run();
		}

	}
	
	/**
	 * The stop method gets called, if the stop button in the GUI gets clicked.
	 * It stops the interaction procedure.
	 */
	public static void stop() {
		isRunning = false;
	}
	
	/**
	 * The run method calls the doMove method in a loop till isRunning == false.
	 */
	public static void run() {
		while (isRunning) {
			try {
				robotInteractionHandler.doMove();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		//Stuff to do if the robot should stop running.
	}
	
	public static void changeInformation(String name, Object value) {
		
	}
}




















