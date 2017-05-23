package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

public class Simulation implements Cloneable{
	private SimulatedRoom room;
	private Robot robot;

	public Simulation(SimulatedRoom room,Robot robot) {
		super();
		this.room = room;
		this.robot= robot;
	}

	public Robot getRobot() {
		return robot;
	}

	public SimulatedRoom getRoom() {
		return room;
	}
	
	@Override 
	public Object clone()
	{
		return new Simulation(room,(Robot) robot.clone());
	}
}
