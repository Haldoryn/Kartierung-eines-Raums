package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

public interface IRobot {

	/**
	 * Sends a message to controller which contains the detected gyroscope
	 * value(distance)
	 * 
	 * @param gyroscopeValue
	 *            The detected gyroscope value
	 */
	void sendReturnGyroscope(float gyroscopeValue);

	/**
	 * Sends a text message to the controller.
	 * 
	 * @param message
	 *            The text message.
	 */
	void sendReturnMessage(String message);

	/**
	 * Sends a message to the controller telling it that the motor movement has
	 * ended. Contains the actually reached distance in motor angle.
	 * 
	 * @param reachedDistanceLeftAngle
	 *            The actually reached distance of the left motor in angle
	 *            degrees.
	 * @param reachedDistanceRightAngle
	 *            The actually reached distance of the right motor in angle
	 *            degrees.
	 */
	void sendReturnMotor(int reachedDistanceLeftAngle, int reachedDistanceRightAngle);

	/**
	 * Sends a message to the controller telling it that the reset of the robot
	 * was completed.
	 * 
	 */
	void sendReturnReset();

	/**
	 * Sends a message to the controller telling it that the rotation of the
	 * sensor motor was completed.
	 * 
	 */
	void sendReturnSensor();

	/**
	 * Sends a message to the controller that contains robot status information.
	 * 
	 * @param batteryPoints
	 *            The current battery points of the robot.
	 */
	void sendReturnStatus(int batteryPoints);

	/**
	 * Sends a message to controller which contains the detected ultrasonic
	 * value(distance)
	 * 
	 * @param value
	 *            The detected ultrasonic value
	 */
	void sendReturnUltrasonic(float ultrasonicValue);
}
