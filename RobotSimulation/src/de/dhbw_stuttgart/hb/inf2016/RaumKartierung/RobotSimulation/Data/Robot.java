package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.awt.geom.Point2D;

public class Robot {
	private double rotation = 0;
	private Point2D position;
	private int size=100;

	public Robot(double rotation, Point2D position,int size) {
		super();
		this.rotation = rotation;
		this.position = position;
		this.size=size;
	}

	public int getSize() {
		return size;
	}

	public double getRotation() {
		return rotation;
	}

	public Point2D getPosition() {
		return position;
	}

}
