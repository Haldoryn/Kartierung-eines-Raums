package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnResetCmd extends CommandBase {

	public ReturnResetCmd() {
		super(CommandType.returnReset);
	}

	public String toString() {
		return super.toString() + ";";
	}
}
