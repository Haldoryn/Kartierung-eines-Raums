package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

/**
 * Interface for the server endpoint of the robot protocol. Defines methods for
 * sending and receiving messages from an to the robot.
 * 
 * @author Julian Vogel
 *
 */
public interface IControler {

	/** Tells the robot to take a rotation measurement. */
	void sendGetGyroscope();

	/** Tells the robot to send a status message. */
	void sendGetStatus();

	/** Tells the robot to take a distance measurement. */
	void sendGetUltrasonic();

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

	/** Tells the robot to reset. Restart the room mapping. */
	void sendReset();
	
	
	/** Tells the robot to take a rotation measurement. 
	 * @param timeoutMs
	 *            Time in that in ms till the a timeout should be detected(throws exception).
	*/
	ReturnGyroscopeCmd sendGetGyroscopeAndWait(int timeoutMs)  throws InterruptedException;

	/** Tells the robot to send a status message. 
	 * @param timeoutMs
	 *            Time in that in ms till the a timeout should be detected(throws exception).
	*/
	ReturnStatusCmd sendGetStatusAndWait(int timeoutMs)  throws InterruptedException;

	/** Tells the robot to take a distance measurement. 
	 * @param timeoutMs
	 *            Time in that in ms till the a timeout should be detected(throws exception).
	*/
	ReturnUltrasonicCmd sendGetUltrasonicAndWait(int timeoutMs)  throws InterruptedException;

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
	 * @param timeoutMs
	 *            Time in that in ms till the a timeout should be detected(throws exception).
	 */
	ReturnMotorCmd sendMoveMotorAndWait(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft, int distanceAngleRight,int timeoutMs)  throws InterruptedException;

	/**
	 * Tells the robot to turn the sensor motor.
	 * 
	 * @param anglePerSecond
	 *            The motor speed(degrees/s),
	 * @param totalAngle
	 *            The total rotation that should be turned by the robot.
	 * @param timeoutMs
	 *            Time in that in ms till the a timeout should be detected(throws exception).
	 */
	ReturnSensorCmd sendMoveSensorAndWait(int anglePerSecond, int totalAngle,int timeoutMs)  throws InterruptedException;

	/** Tells the robot to reset. Restart the room mapping.
	 * @param timeoutMs
	 *            Time in that in ms till the a timeout should be detected(throws exception).
	*/
	ReturnResetCmd sendResetAndWait(int timeoutMs)  throws InterruptedException;
}
