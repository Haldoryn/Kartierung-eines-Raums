package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class GetUltrasonicCmd extends CommandBase {

	public GetUltrasonicCmd() {
		super(CommandType.getUltrasonic);
	}

	public String toString() {
		return super.toString() + ";";
	}
}
