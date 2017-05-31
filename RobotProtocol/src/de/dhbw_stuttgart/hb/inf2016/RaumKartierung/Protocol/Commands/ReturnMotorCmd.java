package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnMotorCmd extends CommandBase {

	private int reachedDistanceAngle = 0;

	public ReturnMotorCmd() {
		super(CommandType.returnMotor);
	}

	public ReturnMotorCmd(int reachedDistanceAngle) {
		super(CommandType.returnMotor);
		this.reachedDistanceAngle = reachedDistanceAngle;
	}

	public int getReachedDistanceAngle() {
		return reachedDistanceAngle;
	}

	public void setReachedDistanceAngle(int reachedDistanceAngle) {
		this.reachedDistanceAngle = reachedDistanceAngle;
	}

	public String toString() {
		return super.toString() + "," + reachedDistanceAngle + ";";
	}

}
