package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**ReturnGyroscopeCmd command.
 * @author JVogel
 *
 */
public class ReturnGyroscopeCmd extends CommandBase {
	private double value = 0;

	/**Initializes a new instance of the ReturnGyroscopeCmd class.
	 * 
	 */
	public ReturnGyroscopeCmd() {
		super(CommandType.returnGyroscope);
	}

	/**Initializes a new instance of the ReturnGyroscopeCmd class.
	 * @param value the value returned by the gyroscope.
	 */
	public ReturnGyroscopeCmd(double value) {
		super(CommandType.returnGyroscope);
		this.value = value;
	}

	
	/**Gets the value that was returned by the gyroscope.
	 * @return The value that was returned by the gyroscope.
	 */
	public double getValue() {
		return value;
	}

	/**Sets the value that was returned by the gyroscope.
	 * @param value The value that was returned by the gyroscope.
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
