package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

/**
 * Interface for the server endpoint of the robot protocol. Defines methods for
 * sending and receiving messages from an to the robot.
 * 
 * @author Julian Vogel
 *
 */
public interface IControler {

	/**
	 * Tells the robot to move.
	 * 
	 * @param anglePerSecondLeft
	 *            Left motor speed(degrees/s).
	 * @param anglePerSecondRight
	 *            Right motor speed(degrees/s).
	 * @param distanceAngleLeft
	 *            The total rotation of the left motor(degrees).
	 * @param distanceAngleRight
	 *            The total rotation of the left motor(degrees).
	 */
	void sendMoveMotor(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft, int distanceAngleRight);

	/**
	 * Tells the robot to turn the sensor motor.
	 * 
	 * @param anglePerSecond
	 *            The motor speed(degrees/s),
	 * @param totalAngle
	 *            The total rotation that should be turned by the robot.
	 */
	void sendMoveSensor(int anglePerSecond, int totalAngle);

	/** Tells the robot to take a distance measurement. */
	void sendGetUltrasonic();

	/** Tells the robot to take a rotation measurement. */
	void sendGetGyroscope();

	/** Tells the robot to reset. Restart the room mapping. */
	void sendReset();

	/** Tells the robot to send a status message. */
	void sendGetStatus();
}
