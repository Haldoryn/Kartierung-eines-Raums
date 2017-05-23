package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.awt.geom.Point2D;

public class Robot {
	private double rotation = 0;
	private Point2D position = new Point2D.Double(0, 0);
	private int size = 100;
	private int sensorRotation = 0;

	public Robot() {
	};

	public int getSize() {
		return size;
	}

	public double getRotation() {
		return rotation;
	}

	public Point2D getPosition() {
		return position;
	}

	public int getSensorRotation() {
		return sensorRotation;
	}
	
	public void move(double x,double y)
	{
		position = new Point2D.Double(position.getX()+x,position.getY()+y);
	}

	@Override
	public Object clone() {
		Robot clone = new Robot();
		clone.position = (Point2D) this.position.clone();
		clone.rotation = this.rotation;
		clone.sensorRotation = this.sensorRotation;
		clone.size = this.size;
		return clone;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSensorRotation(int sensorRotation) {
		this.sensorRotation = sensorRotation;
	}
}
