package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public abstract class CommandBase {
	private CommandType type;

	protected CommandBase(CommandType type) {
		super();
		this.type = type;
	}

	public CommandType getType() {
		return type;
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
