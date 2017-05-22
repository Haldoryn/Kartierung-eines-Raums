package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data;

public class Simulation {
	private SimulatedRoom room;

	public Simulation(SimulatedRoom room) {
		super();
		this.room = room;
	}

	public SimulatedRoom getRoom() {
		return room;
	}
}
