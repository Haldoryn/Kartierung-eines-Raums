package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Constants.Constants;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Controlling;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old.Line;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old.Vector;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old.VectorOperations;

public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Start GUI
		Controller controller = new Controller();
		controller.doMove();
	}
}
