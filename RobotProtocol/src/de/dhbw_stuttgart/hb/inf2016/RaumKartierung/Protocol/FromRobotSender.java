package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

class FromRobotSender implements IFromRobotSender {

	private IProtocolEndpoint protocolEndpoint;

	protected FromRobotSender(IProtocolEndpoint protocolEndpoint) {
		super();
		if (protocolEndpoint == null) {
			throw new IllegalArgumentException("The 'protocolEndpoint' argument must not be null");
		}
		this.protocolEndpoint = protocolEndpoint;
	}

	@Override
	public void sendReturnGyroscope(float gyroscopeValue) {
		CommandBase cmd = new ReturnGyroscopeCmd(gyroscopeValue);
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendReturnMessage(String message) {
		CommandBase cmd = new ReturnMessageCmd(message);
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendReturnMotor(int reachedDistanceLeftAngle, int reachedDistanceRightAngle) {
		CommandBase cmd = new ReturnMotorCmd(reachedDistanceLeftAngle, reachedDistanceRightAngle);
		protocolEndpoint.sendCommand(cmd);
	}

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

	@Override
	public void sendReturnStatus(int batteryPoints) {
		CommandBase cmd = new ReturnStatusCmd(batteryPoints);
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendReturnUltrasonic(float ultrasonicValue) {
		CommandBase cmd = new ReturnUltrasonicCmd(ultrasonicValue);
		protocolEndpoint.sendCommand(cmd);
	}

}
