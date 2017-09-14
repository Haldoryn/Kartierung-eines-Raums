package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;



/**The get gyroscope command.
 * @author JVogel
 *
 */
public class GetGyroscopeCmd extends CommandBase {

	
	/**Initializes a new instance of the GetGyroscopeCmd class.
	 * 
	 */
	public GetGyroscopeCmd() {
		super(CommandType.getGyroscope);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + ";";
	}
}
