package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class GetStatusCmd extends CommandBase {

	public GetStatusCmd() {
		super(CommandType.getStatus);
	}

	public String toString()
	{
		return super.toString()+";";
	}
}
