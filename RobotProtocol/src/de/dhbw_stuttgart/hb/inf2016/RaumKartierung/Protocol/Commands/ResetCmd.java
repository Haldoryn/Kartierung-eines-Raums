package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ResetCmd extends CommandBase {

	public ResetCmd() {
		super(CommandType.reset);
	}

	public String toString()
	{
		return super.toString()+";";
	}	
}
