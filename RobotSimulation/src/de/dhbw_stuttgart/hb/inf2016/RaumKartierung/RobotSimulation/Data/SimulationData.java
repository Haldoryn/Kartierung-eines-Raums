package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

public class SimulationData implements Cloneable{
	private SimulatedRoom room;
	private Robot robot;

	public SimulationData(SimulatedRoom room,Robot robot) {
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
		return new SimulationData(room,(Robot) robot.clone());
	}
}
