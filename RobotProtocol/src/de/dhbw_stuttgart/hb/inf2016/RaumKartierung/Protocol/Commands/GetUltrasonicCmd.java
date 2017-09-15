package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;



/**The GetUltrasonicCmd command.
 * @author JVogel
 *
 */
public class GetUltrasonicCmd extends CommandBase {

	
	/**Initializes a new instance of the GetUltrasonicCmd class.
	 * 
	 */
	public GetUltrasonicCmd() {
		super(CommandType.getUltrasonic);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + ";";
	}
}
