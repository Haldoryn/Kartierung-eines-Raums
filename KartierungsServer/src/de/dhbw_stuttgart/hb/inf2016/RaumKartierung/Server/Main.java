package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Constants.Constants;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Controlling;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old.Line;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old.Vector;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old.VectorOperations;

public class Main {

	// Make Connection with the robo
	// provide an Controler(Class) for other classes ??
	// declare Handling for commandReceived Events
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Start GUI
		// Initialize data model
		// Initialize controller
		
		// while(true && No stop command fom GUI) make the following:
		Controller controller = new Controller();
		controller.doMove();
	}
}
