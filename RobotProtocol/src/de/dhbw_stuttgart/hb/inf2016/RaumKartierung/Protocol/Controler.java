package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

class Controler implements IControler {

	private IProtocolEndpoint protocolEndpoint;

	protected Controler(IProtocolEndpoint protocolEndpoint) {
		super();
		if (protocolEndpoint == null) {
			throw new IllegalArgumentException("The 'protocolEndpoint' argument must not be null");
		}

		this.protocolEndpoint = protocolEndpoint;
	}

	@Override
	public void sendGetGyroscope() {
		CommandBase cmd = new GetGyroscopeCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendGetStatus() {
		CommandBase cmd = new GetStatusCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendGetUltrasonic() {
		CommandBase cmd = new GetUltrasonicCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendMoveMotor(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft,
			int distanceAngleRight) {
		CommandBase cmd = new MoveMotorCmd(anglePerSecondLeft, anglePerSecondRight, distanceAngleLeft,
				distanceAngleRight);
		protocolEndpoint.sendCommand(cmd);

	}

	@Override
	public void sendMoveSensor(int anglePerSecond, int totalAngle) {
		CommandBase cmd = new MoveSensorCmd(anglePerSecond, totalAngle);
		protocolEndpoint.sendCommand(cmd);
	}

	@Override
	public void sendReset() {
		CommandBase cmd = new ResetCmd();
		protocolEndpoint.sendCommand(cmd);
	}

}
