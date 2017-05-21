package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.util.Collections;
import java.util.List;

public class SimulatedRoom {
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
}
