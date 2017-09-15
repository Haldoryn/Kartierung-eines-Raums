package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**
 * The MoveSensorCmd command.
 * 
 * @author JVogel
 *
 */
public class MoveSensorCmd extends CommandBase {

	private int anglePerSecond = 0;
	private int totalAngle = 0;

	/**
	 * Initializes a new instance of the MoveSensorCmd class.
	 * 
	 */
	public MoveSensorCmd() {
		super(CommandType.moveSensor);
	}

	/**
	 * Initializes a new instance of the MoveSensorCmd class.
	 * 
	 * @param anglePerSecond
	 *            The speed of the sensor motor in angle per second.
	 * @param totalAngle
	 *            The total angle that the sensor motor should be rotated.
	 */
	public MoveSensorCmd(int anglePerSecond, int totalAngle) {
		super(CommandType.moveSensor);
		this.anglePerSecond = anglePerSecond;
		this.totalAngle = totalAngle;
	}

	/**
	 * Gets the speed of the sensor motor in angle per second.
	 * 
	 * @return The speed of the sensor motor in angle per second.
	 */
	public int getAnglePerSecond() {
		return anglePerSecond;
	}

	/**
	 * Gets the totalAngle The total angle that the sensor motor should be rotated.
	 * 
	 * @return The totalAngle The total angle that the sensor motor should be
	 *         rotated.
	 */
	public int getTotalAngle() {
		return totalAngle;
	}

	/**
	 * Sets the speed of the sensor motor in angle per second.
	 * 
	 * @param anglePerSecond
	 *            The speed of the sensor motor in angle per second.
	 */
	public void setAnglePerSecond(int anglePerSecond) {
		this.anglePerSecond = anglePerSecond;
	}

	/**
	 * Sets the total angle that the sensor motor should be rotated.
	 * 
	 * @param totalAngle
	 *            The total angle that the sensor motor should be rotated.
	 */
	public void setTotalAngle(int totalAngle) {
		this.totalAngle = totalAngle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#
	 * toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "," + anglePerSecond + "," + totalAngle + ";";
	}

}
