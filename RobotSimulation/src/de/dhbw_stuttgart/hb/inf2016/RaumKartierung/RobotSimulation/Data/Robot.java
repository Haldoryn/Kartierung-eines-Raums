package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.awt.geom.Point2D;

public class Robot {
	private double rotation;
	private Point2D position;
	private int size=100;
	private int sensorRotation =0;

	public Robot(double rotation, Point2D position,int size) {
		super();
		this.rotation = rotation;
		this.position = position;
		this.size=size;
	}
	
	//Constructor for cloning.
	private Robot(){};

	public int getSize() {
		return size;
	}

	public double getRotation() {
		return rotation;
	}

	public Point2D getPosition() {
		return position;
	}

	public int getSensorRotation()
	{
		return sensorRotation;
	}
	
	@Override 
	public Object clone()
	{
		Robot clone = new Robot();
		clone.position=(Point2D) this.position.clone();
		clone.rotation= this.rotation;
		clone.sensorRotation= this.sensorRotation;
		clone.size= this.size;
		return clone;
	}
}
