package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The ReturnResetCmd command.
 * @author JVogel
 *
 */
public class ReturnResetCmd extends CommandBase {

	/**Initializes a new instance of the ReturnResetCmd class.
	 * 
	 */
	public ReturnResetCmd() {
		super(CommandType.returnReset);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + ";";
	}
}
