package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The ReturnMotorCmd command.
 * @author JVogel
 *
 */
public class ReturnMotorCmd extends CommandBase {

	private int reachedRightDistanceAngle = 0;
	private int reachedLeftDistanceAngle = 0;

	/**Initializes a new instance of the ReturnMotorCmd class.
	 * 
	 */
	public ReturnMotorCmd() {
		super(CommandType.returnMotor);
	}

	/**Initializes a new instance of the ReturnMotorCmd class.
	 * @param reachedDistanceLeftAngle The angle that was reached by the left motor.
	 * @param reachedDistanceRightAngle The angle that was reached by the right motor.
	 */
	public ReturnMotorCmd(int reachedDistanceLeftAngle, int reachedDistanceRightAngle) {
		super(CommandType.returnMotor);
		this.reachedLeftDistanceAngle = reachedDistanceLeftAngle;
		this.reachedRightDistanceAngle = reachedDistanceRightAngle;
	}

	/**Gets the angle that was reached by the left motor.
	 * @return The angle that was reached by the left motor.
	 */
	public int getReachedLeftDistanceAngle() {
		return reachedLeftDistanceAngle;
	}

	/**Gets the angle that was reached by the right motor.
	 * @return The angle that was reached by the right motor.
	 */
	public int getReachedRightDistanceAngle() {
		return reachedRightDistanceAngle;
	}

	/**Sets the angle that was reached by the left motor.
	 * @param reachedLeftDistanceAngle The angle that was reached by the left motor.
	 */
	public void setReachedLeftDistanceAngle(int reachedLeftDistanceAngle) {
		this.reachedLeftDistanceAngle = reachedLeftDistanceAngle;
	}

	/**Sets the angle that was reached by the right motor.
	 * @param reachedLeftDistanceAngle The angle that was reached by the right motor.
	 */
	public void setReachedRightDistanceAngle(int reachedRightDistanceAngle) {
		this.reachedRightDistanceAngle = reachedRightDistanceAngle;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + "," + reachedLeftDistanceAngle + "," + reachedRightDistanceAngle + ";";
	}

}
