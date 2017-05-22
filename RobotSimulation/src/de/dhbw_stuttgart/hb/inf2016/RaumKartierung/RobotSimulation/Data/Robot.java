package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.awt.geom.Point2D;

public class Robot {
	private double rotation = 0;
	private Point2D position;

	public Robot(double rotation, Point2D position) {
		super();
		this.rotation = rotation;
		this.position = position;
	}

	public double getRotation() {
		return rotation;
	}

	public Point2D getPosition() {
		return position;
	}

}
