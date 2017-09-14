package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The ReturnSensorCmd command.
 * @author JVogel
 *
 */
public class ReturnSensorCmd extends CommandBase {

	/**Initializes a new instance of the ReturnSensorCmd class.
	 * 
	 */
	public ReturnSensorCmd() {
		super(CommandType.returnSensor);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + ";";
	}
}
