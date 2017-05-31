package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnStatusCmd extends CommandBase {
	private int batteryPoints = 0;

	public ReturnStatusCmd() {
		super(CommandType.returnStatus);
	}

	public ReturnStatusCmd(int batteryPoints) {
		super(CommandType.returnStatus);
		this.batteryPoints = batteryPoints;
	}

	public int getBatteryPoints() {
		return batteryPoints;
	}

	public void setBatteryPoints(int batteryPoints) {
		this.batteryPoints = batteryPoints;
	}

	public String toString() {
		return super.toString() + "," + batteryPoints + ";";
	}
}
