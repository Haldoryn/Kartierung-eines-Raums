package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.awt.geom.Point2D;
import java.util.List;

public class Obstacle extends UltrasonicDetectable {

	private String name;
	
	public Obstacle(List<Point2D> definingPoints,String name) {
		super(definingPoints);
		this.name = name;
		
		if(name ==null)
		{
			this.name= "Unnamed";
		}
	}

	public String getName() {
		return name;
	}

	@Override 
	public Object clone()
	{
		return new Obstacle(definingPoints,name);
	}
}
