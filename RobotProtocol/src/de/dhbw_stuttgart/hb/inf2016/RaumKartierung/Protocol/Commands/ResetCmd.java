package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The ResetCmd command.
 * @author JVogel
 *
 */
public class ResetCmd extends CommandBase {

	/**Initializes a new instance of the ResetCmd
	 * 
	 */
	public ResetCmd() {
		super(CommandType.reset);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + ";";
	}
}
