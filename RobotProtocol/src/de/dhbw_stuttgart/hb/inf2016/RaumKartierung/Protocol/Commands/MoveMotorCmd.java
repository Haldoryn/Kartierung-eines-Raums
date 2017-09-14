package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The MoveMotorCmd command.
 * @author JVogel
 *
 */
public class MoveMotorCmd extends CommandBase {
	private int anglePerSecondLeft = 0;
	private int anglePerSecondRight = 0;
	private int distanceAngleLeft = 0;
	private int distanceAngleRight = 0;

	
	/**Initializes a new instance of the MoveMotorCmd class.
	 * 
	 */
	public MoveMotorCmd() {
		super(CommandType.moveMotor);
	}

	/**Initializes a new instance of the MoveMotorCmd class.
	 * @param anglePerSecondLeft Speed of the left motor in angle per second.
	 * @param anglePerSecondRight Speed of the right motor in angle per second.
	 * @param distanceAngleLeft The total distance that the left motor should move.
	 * @param distanceAngleRight The total distance that the right motor should move.
	 */
	public MoveMotorCmd(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft,
			int distanceAngleRight) {
		super(CommandType.moveMotor);
		this.anglePerSecondLeft = anglePerSecondLeft;
		this.anglePerSecondRight = anglePerSecondRight;
		this.distanceAngleLeft = distanceAngleLeft;
		this.distanceAngleRight = distanceAngleRight;
	}

	
	/**Gets the speed of the left motor in angle per second.
	 * @return The speed of the left motor in angle per second.
	 */
	public int getAnglePerSecondLeft() {
		return anglePerSecondLeft;
	}

	/**Gets the speed of the right motor in angle per second.
	 * @return The speed of the right motor in angle per second.
	 */
	public int getAnglePerSecondRight() {
		return anglePerSecondRight;
	}

	/**Gets the total distance that the left motor should move.
	 * @return The total distance that the left motor should move.
	 */
	public int getDistanceAngleLeft() {
		return distanceAngleLeft;
	}

	/**Gets the total distance that the right motor should move.
	 * @return The total distance that the right motor should move.
	 */
	public int getDistanceAngleRight() {
		return distanceAngleRight;
	}

	/**Sets the speed of the left motor in angle per second.
	 * @param anglePerSecondLeft The speed of the left motor in angle per second.
	 */
	public void setAnglePerSecondLeft(int anglePerSecondLeft) {
		this.anglePerSecondLeft = anglePerSecondLeft;
	}

	/**Sets the speed of the right motor in angle per second.
	 * @param anglePerSecondLeft The speed of the right motor in angle per second.
	 */
	public void setAnglePerSecondRight(int anglePerSecondRight) {
		this.anglePerSecondRight = anglePerSecondRight;
	}

	
	/**Sets the total distance that the left motor should move.
	 * @param distanceAngleLeft The total distance that the left motor should move.
	 */
	public void setDistanceAngleLeft(int distanceAngleLeft) {
		this.distanceAngleLeft = distanceAngleLeft;
	}

	/**Sets the total distance that the right motor should move.
	 * @param distanceAngleLeft The total distance that the right motor should move.
	 */
	public void setDistanceAngleRight(int distanceAngleRight) {
		this.distanceAngleRight = distanceAngleRight;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + "," + anglePerSecondLeft + "," + anglePerSecondRight + "," + distanceAngleLeft + ","
				+ distanceAngleRight + ";";
	}
}
