package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Obstacle extends UltrasonicDetectable {

	private String name;
	
	public Obstacle(ArrayList<Point2D> definingPoints,String name) {
		super(definingPoints);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
