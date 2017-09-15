package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

/**Default implementation of the IFromRobotSender interface. Can be used to send the robot responses.
 * @author JVogel
 *
 */
class FromRobotSender implements IFromRobotSender {

	private IProtocolEndpoint protocolEndpoint;

	/**Initializes a new instance of the IProtocolEndpoint class.
	 * @param protocolEndpoint The IProtocolEndpoint that the new instance of IProtocolEndpoint used to send commands.
	 */
	protected FromRobotSender(IProtocolEndpoint protocolEndpoint) {
		super();
		if (protocolEndpoint == null) {
			throw new IllegalArgumentException("The 'protocolEndpoint' argument must not be null");
		}
		this.protocolEndpoint = protocolEndpoint;
	}

	
	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IFromRobotSender#sendReturnGyroscope(float)
	 */
	@Override
	public void sendReturnGyroscope(float gyroscopeValue) {
		CommandBase cmd = new ReturnGyroscopeCmd(gyroscopeValue);
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IFromRobotSender#sendReturnMessage(java.lang.String)
	 */
	@Override
	public void sendReturnMessage(String message) {
		CommandBase cmd = new ReturnMessageCmd(message);
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IFromRobotSender#sendReturnMotor(int, int)
	 */
	@Override
	public void sendReturnMotor(int reachedDistanceLeftAngle, int reachedDistanceRightAngle) {
		CommandBase cmd = new ReturnMotorCmd(reachedDistanceLeftAngle, reachedDistanceRightAngle);
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IFromRobotSender#sendReturnReset()
	 */
	@Override
	public void sendReturnReset() {
		CommandBase cmd = new ReturnResetCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendReturnSensor() {
		CommandBase cmd = new ReturnSensorCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IFromRobotSender#sendReturnStatus(int)
	 */
	@Override
	public void sendReturnStatus(int batteryPoints) {
		CommandBase cmd = new ReturnStatusCmd(batteryPoints);
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IFromRobotSender#sendReturnUltrasonic(float)
	 */
	@Override
	public void sendReturnUltrasonic(float ultrasonicValue) {
		CommandBase cmd = new ReturnUltrasonicCmd(ultrasonicValue);
		protocolEndpoint.sendCommand(cmd);
	}

}
