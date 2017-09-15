package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class GetStatusCmd extends CommandBase {

	/**
	 * Initializes a new instance of the GetStatusCmd class.
	 * 
	 */
	public GetStatusCmd() {
		super(CommandType.getStatus);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + ";";
	}
}
