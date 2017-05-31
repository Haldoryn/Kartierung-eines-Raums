package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class GetGyroscopeCmd extends CommandBase {

	public GetGyroscopeCmd() {
		super(CommandType.getGyroscope);
	}

	public String toString() {
		return super.toString() + ";";
	}
}
