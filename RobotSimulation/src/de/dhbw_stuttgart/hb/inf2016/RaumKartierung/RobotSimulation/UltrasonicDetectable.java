package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class UltrasonicDetectable {
	protected ArrayList<Point2D> definingPoints;

	public UltrasonicDetectable(ArrayList<Point2D> definingPoints) {
		super();
		this.definingPoints = definingPoints;
	}
}
