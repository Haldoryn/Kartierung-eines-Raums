package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnGyroscopeCmd extends CommandBase {
	private double value = 0;

	public ReturnGyroscopeCmd() {
		super(CommandType.returnGyroscope);
	}

	public ReturnGyroscopeCmd(double value) {
		super(CommandType.returnGyroscope);
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
