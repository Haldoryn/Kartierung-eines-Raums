package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnUltrasonicCmd extends CommandBase {

	private double value = 0;

	public ReturnUltrasonicCmd() {
		super(CommandType.returnUltrasonic);
	}

	public ReturnUltrasonicCmd(double value) {
		super(CommandType.returnUltrasonic);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String toString() {
		return super.toString() + "," + value + ";";
	}
}
