package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class UltrasonicDetectable {
	protected List<Point2D> definingPoints;

	public UltrasonicDetectable(List<Point2D> definingPoints) {
		super();
		this.definingPoints = new ArrayList<Point2D>(definingPoints);
	}
}
