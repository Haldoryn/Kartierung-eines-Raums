package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.util.Hashtable;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

class Controler implements IControler {

	private Hashtable<CommandType, PaketNotifier> notifiers = new Hashtable<>();

	private IProtocolEndpoint protocolEndpoint;

	protected Controler(IProtocolEndpoint protocolEndpoint) {
		super();
		if (protocolEndpoint == null) {
			throw new IllegalArgumentException("The 'protocolEndpoint' argument must not be null");
		}

		this.protocolEndpoint = protocolEndpoint;

		// Initialize the wait object table.
		for (CommandType type : CommandType.values()) {
			notifiers.put(type, new PaketNotifier());
		}

		// When a new paket is received, inform all potentially waiting threads.
		protocolEndpoint.addCommandListener(new ICommandReceiver() {

			@Override
			public void commandReceived(CommandBase cmd) {
				PaketNotifier notifier = notifiers.get(cmd.getType());
				notifier.setValue(cmd);
				notifier.notify();
			}
		});
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

	@Override
	public ReturnGyroscopeCmd sendGetGyroscopeAndWait(int timeoutMs) throws InterruptedException {
		sendGetGyroscope();
		PaketNotifier notifier = notifiers.get(CommandType.getGyroscope);
		notifier.wait(timeoutMs);
		return (ReturnGyroscopeCmd) notifier.getValue();
	}

	@Override
	public ReturnStatusCmd sendGetStatusAndWait(int timeoutMs) throws InterruptedException {
		sendGetStatus();
		PaketNotifier notifier = notifiers.get(CommandType.getStatus);
		notifier.wait(timeoutMs);
		return (ReturnStatusCmd) notifier.getValue();
	}

	@Override
	public ReturnUltrasonicCmd sendGetUltrasonicAndWait(int timeoutMs) throws InterruptedException {
		sendGetUltrasonic();
		PaketNotifier notifier = notifiers.get(CommandType.getUltrasonic);
		notifier.wait(timeoutMs);
		return (ReturnUltrasonicCmd) notifier.getValue();
	}

	@Override
	public ReturnMotorCmd sendMoveMotorAndWait(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft,
			int distanceAngleRight, int timeoutMs) throws InterruptedException {
		sendMoveMotor(anglePerSecondLeft, anglePerSecondRight, distanceAngleLeft, distanceAngleRight);
		PaketNotifier notifier = notifiers.get(CommandType.moveMotor);
		notifier.wait(timeoutMs);
		return (ReturnMotorCmd) notifier.getValue();
	}

	@Override
	public ReturnSensorCmd sendMoveSensorAndWait(int anglePerSecond, int totalAngle, int timeoutMs) throws InterruptedException {
		sendMoveSensor(anglePerSecond,totalAngle);
		PaketNotifier notifier = notifiers.get(CommandType.moveSensor);
		notifier.wait(timeoutMs);
		return (ReturnSensorCmd) notifier.getValue();
	}

	@Override
	public ReturnResetCmd sendResetAndWait(int timeoutMs) throws InterruptedException {
		sendReset();
		PaketNotifier notifier = notifiers.get(CommandType.reset);
		notifier.wait(timeoutMs);
		return (ReturnResetCmd) notifier.getValue();
	}

}
