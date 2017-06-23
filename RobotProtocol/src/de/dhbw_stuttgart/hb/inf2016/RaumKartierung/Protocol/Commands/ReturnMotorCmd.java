package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnMotorCmd extends CommandBase {

	private int reachedRightDistanceAngle = 0;
	private int reachedLeftDistanceAngle = 0;

	public ReturnMotorCmd() {
		super(CommandType.returnMotor);
	}

	public ReturnMotorCmd(int reachedDistanceLeftAngle, int reachedDistanceRightAngle) {
		super(CommandType.returnMotor);
		this.reachedLeftDistanceAngle = reachedDistanceLeftAngle;
		this.reachedRightDistanceAngle = reachedDistanceRightAngle;
	}

	public int getReachedLeftDistanceAngle() {
		return reachedLeftDistanceAngle;
	}

	public int getReachedRightDistanceAngle() {
		return reachedRightDistanceAngle;
	}

	public void setReachedLeftDistanceAngle(int reachedLeftDistanceAngle) {
		this.reachedLeftDistanceAngle = reachedLeftDistanceAngle;
	}

	public void setReachedRightDistanceAngle(int reachedRightDistanceAngle) {
		this.reachedRightDistanceAngle = reachedRightDistanceAngle;
	}

	public String toString() {
		return super.toString() + "," + reachedLeftDistanceAngle + "," + reachedRightDistanceAngle + ";";
	}

}
