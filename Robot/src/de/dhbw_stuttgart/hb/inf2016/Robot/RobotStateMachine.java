package de.dhbw_stuttgart.hb.inf2016.Robot;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ICommandReceiver;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IRobot;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.MoveMotorCmd;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.MoveSensorCmd;
import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

/**
 * State machine of the robot. The robot can only do one thing at a time and
 * each of the states i n this state machine defines the what the robot has to
 * do in that state.
 *
 * The robot starts with the initialization state and moves to its main step
 * which is the AwaitingCommand state. From this state all the other
 * states(except init) can be reached. All states exept the reset state return
 * to the AwaitingCommand state.
 * 
 * @author Julian Vogel
 */
public class RobotStateMachine implements ICommandReceiver {

	// Interface to the robot protocol
	private IRobot responseSender;
	// The current state
	private State currentState = State.Init;
	// The command that should be executed.
	private CommandBase currentCommand;

	// The objects that are used to access the robot hardware.
	private RangeFinder sonar;
	private RegulatedMotor sensorMotor;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private EV3GyroSensor gyro;

	public RobotStateMachine(IRobot robotCommands) {
		responseSender = robotCommands;

		sonar = new RangeFinderAdapter(new EV3UltrasonicSensor(SensorPort.S1));
		DisplayConsole.writeString("Ultrasonic OK");
		
		sensorMotor = new EV3MediumRegulatedMotor(MotorPort.A);
		DisplayConsole.writeString("Sensor Motor Ok");
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		DisplayConsole.writeString("Left Motor OK");
		
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		DisplayConsole.writeString("Right Motor Ok");
		
		gyro = new EV3GyroSensor(SensorPort.S2);
		DisplayConsole.writeString("Gyro Sensor Ok");

		// Hardware calibration of the Gyro sensor and reset off accumulated angle to
		// zero. From no on the angle is tracked
		gyro.reset();
		DisplayConsole.writeString("Gyro Reset Ok");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ICommandReceiver#
	 * commandReceived(de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands
	 * .CommandBase)
	 */
	@Override
	public void commandReceived(CommandBase cmd) {
		if (currentCommand != null) {
			responseSender.sendReturnMessage("Robot is busy with " + currentCommand.getType().toString());
		}

		currentCommand = cmd;
	}

	/**
	 * @author Julian Vogel Enum that defines all states of the robot state machine
	 */
	private enum State {
		Init, Reset, AwaitingCommand, MovingSensor, Moving, ScanUltrasonic, ScanGyroscope, Status
	}

	/**
	 * Runs the state machine. Returns if the state machine is exited by sending the
	 * reset command or by pressing the escape button on the robot.
	 * 
	 * @throws Exception
	 *             Thrown if a undefined state is reached.
	 */
	public void runStatemachine() throws Exception {
		while (!Button.ESCAPE.isDown()) {
			switch (currentState) {
			case AwaitingCommand:
				DoAwaitCommand();
				break;
			case Init:
				responseSender.sendReturnMessage("Initializing robot state machine");
				sensorMotor.rotateTo(0);
				currentState = State.AwaitingCommand;
				break;
			case Moving:
				DoMoveMotor();
				break;
			case MovingSensor:
				DoMoveSensor();
				break;
			case Reset:
				responseSender.sendReturnMessage("Exiting robot state machine");
				return;
			case ScanGyroscope:
				DoGyroscopeScan();
				break;
			case ScanUltrasonic:
				responseSender.sendReturnUltrasonic(sonar.getRange());
				currentState = State.AwaitingCommand;
				break;
			case Status:
				responseSender.sendReturnStatus(Battery.getVoltageMilliVolt());
				currentState = State.AwaitingCommand;
				break;
			default:
				throw new Exception("Unknown state");
			}
		}
	}

	/**
	 * Handles the AwaitCommand state.
	 * 
	 */
	private void DoAwaitCommand() {
		if (currentCommand != null) {
			switch (currentCommand.getType()) {
			case getGyroscope:
				currentState = State.ScanGyroscope;
				break;
			case getStatus:
				currentState = State.Status;
				break;
			case getUltrasonic:
				currentState = State.ScanUltrasonic;
				break;
			case moveMotor:
				currentState = State.Moving;
				break;
			case moveSensor:
				currentState = State.MovingSensor;
				break;
			case reset:
				currentState = State.Reset;
				break;
			default:
				responseSender.sendReturnMessage(
						"Robot can not handle commands of type:" + currentCommand.getType().toString());
				currentCommand = null;
				break;
			}
			return;
		}
		Delay.msDelay(1);
	}

	/**
	 * Handles the MoveSensor state.
	 * 
	 */
	private void DoMoveSensor() {
		MoveSensorCmd cmd = ((MoveSensorCmd) currentCommand);

		if (cmd.getAnglePerSecond() > sensorMotor.getMaxSpeed()) {
			responseSender.sendReturnMessage(
					"The robot can not rotate the sensor at this speed with the current battery level");
		}

		sensorMotor.setSpeed(cmd.getAnglePerSecond());
		sensorMotor.rotate(cmd.getTotalAngle());

		// Set state and send answer
		currentCommand = null;
		currentState = State.AwaitingCommand;
		responseSender.sendReturnSensor();
	}

	/**
	 * Handles the move motor state
	 * 
	 */
	private void DoMoveMotor() {
		MoveMotorCmd cmd = ((MoveMotorCmd) currentCommand);

		if (cmd.getAnglePerSecondLeft() > leftMotor.getMaxSpeed()) {
			responseSender.sendReturnMessage(
					"The robot can not rotate the left motor at this speed with the current battery level");
		}
		if (cmd.getAnglePerSecondRight() > rightMotor.getMaxSpeed()) {
			responseSender.sendReturnMessage(
					"The robot can not rotate the right motor at this speed with the current battery level");
		}

		leftMotor.setSpeed(cmd.getAnglePerSecondLeft());
		rightMotor.setSpeed(cmd.getAnglePerSecondRight());

		leftMotor.rotate(cmd.getDistanceAngleLeft(), true);
		rightMotor.rotate(cmd.getDistanceAngleRight(), true);

		while (leftMotor.isMoving() || rightMotor.isMoving()) {
			// ToDo at check for obstacles here.
			Delay.msDelay(1);
		}

		// Set state and send answer
		currentCommand = null;
		currentState = State.AwaitingCommand;
		responseSender.sendReturnMotor(cmd.getDistanceAngleLeft(), cmd.getDistanceAngleRight());
	}

	/**
	 * Handles the ScanGyroscope state.
	 * 
	 */
	private void DoGyroscopeScan() {
		float[] sample = new float[1];
		SampleProvider angleProvider = gyro.getAngleMode();
		angleProvider.fetchSample(sample, 0);

		// Set state and send answer
		currentCommand = null;
		currentState = State.AwaitingCommand;
		responseSender.sendReturnGyroscope(sample[0]);
	}
}
