package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class MoveSensorCmd extends CommandBase {

	private int anglePerSecond = 0;
	private int totalAngle = 0;

	public MoveSensorCmd() {
		super(CommandType.moveSensor);
	}

	public MoveSensorCmd(int anglePerSecond, int totalAngle) {
		super(CommandType.moveSensor);
		this.anglePerSecond = anglePerSecond;
		this.totalAngle = totalAngle;
	}

	public int getAnglePerSecond() {
		return anglePerSecond;
	}

	public int getTotalAngle() {
		return totalAngle;
	}

	public void setAnglePerSecond(int anglePerSecond) {
		this.anglePerSecond = anglePerSecond;
	}

	public void setTotalAngle(int totalAngle) {
		this.totalAngle = totalAngle;
	}

	@Override
	public String toString() {
		return super.toString() + "," + anglePerSecond + "," + totalAngle + ";";
	}

}
