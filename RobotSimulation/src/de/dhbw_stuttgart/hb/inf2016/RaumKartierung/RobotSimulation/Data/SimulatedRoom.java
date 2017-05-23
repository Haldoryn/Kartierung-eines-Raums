package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulatedRoom implements Cloneable {
	private Outline outline;
	private List<Obstacle> obstacles;
	private String name;
	
	public SimulatedRoom(Outline outline, List<Obstacle> obstacles,String name) {
		super();
		this.outline = outline;
		this.obstacles = obstacles;
		this.name= name;
	}

	public String getName() {
		return name;
	}

	public Outline getOutline() {
		return outline;
	}

	public List<Obstacle> getObstacles() {
		return  Collections.unmodifiableList(obstacles);
	}
	
	@Override 
	public Object clone()
	{
		List<Obstacle> clonedObstacles = new ArrayList<Obstacle>();
		for(Obstacle obstacle:obstacles)
		{
			clonedObstacles.add((Obstacle)obstacle.clone());
		}
		
		return new SimulatedRoom((Outline)outline.clone(), clonedObstacles, name);
	}
}
