package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The ReturnUltrasonicCmd command.
 * @author JVogel
 *
 */
public class ReturnUltrasonicCmd extends CommandBase {

	private double value = 0;

	/**Initializes a new instance of the ReturnUltrasonicCmd class.
	 * 
	 */
	public ReturnUltrasonicCmd() {
		super(CommandType.returnUltrasonic);
	}

	/**Initializes a new instance of the ReturnUltrasonicCmd class.
	 * @param value The value that was returned by the ultrasonic sensor.
	 */
	public ReturnUltrasonicCmd(double value) {
		super(CommandType.returnUltrasonic);
		this.value = value;
	}

	/**Gets the value that was returned by the ultrasonic sensor.
	 * @return The value that was returned by the ultrasonic sensor.
	 */
	public double getValue() {
		return value;
	}

	/**Sets the value that was returned by the ultrasonic sensor.
	 * @param value The value that was returned by the ultrasonic sensor.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + "," + value + ";";
	}
}
