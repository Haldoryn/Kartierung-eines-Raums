package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.awt.geom.Point2D;
import java.util.List;

public class Outline extends UltrasonicDetectable {

	public Outline(List<Point2D> definingPoints) {
		super(definingPoints);
	}

	@Override 
	public Object clone()
	{
		return new Outline(definingPoints);
	}
}
