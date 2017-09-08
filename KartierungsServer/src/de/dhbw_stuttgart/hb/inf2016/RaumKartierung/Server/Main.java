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

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.*;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandType;


public class Main {

	private static RobotInteractionHandler robotInteractionHandler;
	private static boolean allowRobotToMove;
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
		
		
		int test =0;
		while(test==0) {}
				
		try {
			robotInteractionHandler = new RobotInteractionHandler();
			/*
			 * This while loop runs as long the program runs. 
			 * In the future it should run as long the GUI says something else.
			 */
			while(allowRobotToMove) {
				robotInteractionHandler.doMove();
			}

		} catch (Exception e) {
			/*
			 * I don't know jet what we need in this catch block.
			 */
			e.printStackTrace();
		}
	}
	public static void start() {
		
	}
	public static void stop() {
		
	}
}




















