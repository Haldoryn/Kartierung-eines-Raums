package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.util.Hashtable;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

/**Default implementation of the IToRobotSender. Can be used to send commands to the robot.
 * @author JVogel
 *
 */
class ToRobotSender implements IToRobotSender {

	/**All PaketNotifiers that are used to wait for responses by the type of command that they can be used to wait for.
	 * 
	 */
	private Hashtable<CommandType, PaketNotifier> notifiers = new Hashtable<>();

	private IProtocolEndpoint protocolEndpoint;

	/**Initializes a new instance of the ToRobotSender class.
	 * @param protocolEndpoint The endpoint that should be used to send and receive commands.
	 */
	protected ToRobotSender(IProtocolEndpoint protocolEndpoint) {
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
				synchronized (notifier) {
					notifier.setValue(cmd);
					notifier.notify();
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendGetGyroscope()
	 */
	@Override
	public void sendGetGyroscope() {
		CommandBase cmd = new GetGyroscopeCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendGetStatus()
	 */
	@Override
	public void sendGetStatus() {
		CommandBase cmd = new GetStatusCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendGetUltrasonic()
	 */
	@Override
	public void sendGetUltrasonic() {
		CommandBase cmd = new GetUltrasonicCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendMoveMotor(int, int, int, int)
	 */
	@Override
	public void sendMoveMotor(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft,
			int distanceAngleRight) {
		CommandBase cmd = new MoveMotorCmd(anglePerSecondLeft, anglePerSecondRight, distanceAngleLeft,
				distanceAngleRight);
		protocolEndpoint.sendCommand(cmd);

	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendMoveSensor(int, int)
	 */
	@Override
	public void sendMoveSensor(int anglePerSecond, int totalAngle) {
		CommandBase cmd = new MoveSensorCmd(anglePerSecond, totalAngle);
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendReset()
	 */
	@Override
	public void sendReset() {
		CommandBase cmd = new ResetCmd();
		protocolEndpoint.sendCommand(cmd);
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendGetGyroscopeAndWait(int)
	 */
	@Override
	public ReturnGyroscopeCmd sendGetGyroscopeAndWait(int timeoutMs) throws InterruptedException {
		sendGetGyroscope();
		PaketNotifier notifier = notifiers.get(CommandType.returnGyroscope);
		synchronized (notifier) {
			notifier.wait(timeoutMs);
			return (ReturnGyroscopeCmd) notifier.getValue();
		}
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendGetStatusAndWait(int)
	 */
	@Override
	public ReturnStatusCmd sendGetStatusAndWait(int timeoutMs) throws InterruptedException {
		sendGetStatus();
		PaketNotifier notifier = notifiers.get(CommandType.returnStatus);
		synchronized (notifier) {
			notifier.wait(timeoutMs);
			return (ReturnStatusCmd) notifier.getValue();
		}
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendGetUltrasonicAndWait(int)
	 */
	@Override
	public ReturnUltrasonicCmd sendGetUltrasonicAndWait(int timeoutMs) throws InterruptedException {
		sendGetUltrasonic();
		PaketNotifier notifier = notifiers.get(CommandType.returnUltrasonic);
		synchronized (notifier) {
			notifier.wait(timeoutMs);
			return (ReturnUltrasonicCmd) notifier.getValue();
		}
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendMoveMotorAndWait(int, int, int, int, int)
	 */
	@Override
	public ReturnMotorCmd sendMoveMotorAndWait(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft,
			int distanceAngleRight, int timeoutMs) throws InterruptedException {
		sendMoveMotor(anglePerSecondLeft, anglePerSecondRight, distanceAngleLeft, distanceAngleRight);
		PaketNotifier notifier = notifiers.get(CommandType.returnMotor);

		synchronized (notifier) {
			notifier.wait(timeoutMs);
			return (ReturnMotorCmd) notifier.getValue();
		}

	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendMoveSensorAndWait(int, int, int)
	 */
	@Override
	public ReturnSensorCmd sendMoveSensorAndWait(int anglePerSecond, int totalAngle, int timeoutMs)
			throws InterruptedException {
		sendMoveSensor(anglePerSecond, totalAngle);
		PaketNotifier notifier = notifiers.get(CommandType.returnSensor);
		synchronized (notifier) {
			notifier.wait(timeoutMs);
			return (ReturnSensorCmd) notifier.getValue();
		}
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender#sendResetAndWait(int)
	 */
	@Override
	public ReturnResetCmd sendResetAndWait(int timeoutMs) throws InterruptedException {
		sendReset();
		PaketNotifier notifier = notifiers.get(CommandType.returnReset);
		synchronized (notifier) {
			notifier.wait(timeoutMs);
			return (ReturnResetCmd) notifier.getValue();
		}
	}

}
