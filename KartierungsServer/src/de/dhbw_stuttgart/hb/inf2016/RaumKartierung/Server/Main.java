package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Line;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Vector;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorOperations;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VectorOperations VO = new VectorOperations();
		Line line = new Line (new Vector(1,1), new Vector(3,3));
		System.out.println(line.getVectorAtY(2).getX());
	}

}
