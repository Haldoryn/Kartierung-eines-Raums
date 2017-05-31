package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class MoveMotorCmd extends CommandBase {
	private int anglePerSecondLeft = 0;
	private int anglePerSecondRight = 0;
	private int distanceAngleLeft = 0;
	private int distanceAngleRight = 0;

	public MoveMotorCmd() {
		super(CommandType.moveMotor);
	}

	public MoveMotorCmd(int anglePerSecondLeft, int anglePerSecondRight, int distanceAngleLeft,
			int distanceAngleRight) {
		super(CommandType.moveMotor);
		this.anglePerSecondLeft = anglePerSecondLeft;
		this.anglePerSecondRight = anglePerSecondRight;
		this.distanceAngleLeft = distanceAngleLeft;
		this.distanceAngleRight = distanceAngleRight;
	}

	public int getAnglePerSecondLeft() {
		return anglePerSecondLeft;
	}

	public int getAnglePerSecondRight() {
		return anglePerSecondRight;
	}

	public int getDistanceAngleLeft() {
		return distanceAngleLeft;
	}

	public int getDistanceAngleRight() {
		return distanceAngleRight;
	}

	public void setAnglePerSecondLeft(int anglePerSecondLeft) {
		this.anglePerSecondLeft = anglePerSecondLeft;
	}

	public void setAnglePerSecondRight(int anglePerSecondRight) {
		this.anglePerSecondRight = anglePerSecondRight;
	}

	public void setDistanceAngleLeft(int distanceAngleLeft) {
		this.distanceAngleLeft = distanceAngleLeft;
	}

	public void setDistanceAngleRight(int distanceAngleRight) {
		this.distanceAngleRight = distanceAngleRight;
	}

	public String toString() {
		return super.toString() + "," + anglePerSecondLeft + "," + anglePerSecondRight + "," + distanceAngleLeft + ","
				+ distanceAngleRight + ";";
	}
}
