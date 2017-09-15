package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The {@link ReturnStatusCmd} command
 * @author JVogel
 *
 */
public class ReturnStatusCmd extends CommandBase {
	private int batteryPoints = 0;

	/**Initializes a new instance of the ReturnStatusCmd class.
	 * 
	 */
	public ReturnStatusCmd() {
		super(CommandType.returnStatus);
	}

	/**Initializes a new instance of the ReturnStatusCmd class.
	 * @param batteryPoints the current battery point of the robot.
	 */
	public ReturnStatusCmd(int batteryPoints) {
		super(CommandType.returnStatus);
		this.batteryPoints = batteryPoints;
	}

	
	/**Gets the current battery points of the robot.
	 * @return
	 */
	public int getBatteryPoints() {
		return batteryPoints;
	}

	/**Sets the current battery points of the robot.
	 * @return
	 */
	public void setBatteryPoints(int batteryPoints) {
		this.batteryPoints = batteryPoints;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + "," + batteryPoints + ";";
	}
}
