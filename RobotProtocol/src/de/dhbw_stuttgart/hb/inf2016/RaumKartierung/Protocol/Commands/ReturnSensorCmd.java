package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnSensorCmd extends CommandBase {

	public ReturnSensorCmd() {
		super(CommandType.returnSensor);
	}

	public String toString() {
		return super.toString() + ";";
	}
}
