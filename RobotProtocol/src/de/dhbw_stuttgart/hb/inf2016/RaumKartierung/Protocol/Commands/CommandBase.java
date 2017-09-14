package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**
 * Base class of all protocol commands.
 * 
 * @author JVogel
 *
 */
public abstract class CommandBase {
	//Stores the type of the new command.
	private CommandType type;

	/**
	 * Initializes a new instance of the CommandBase class.
	 * 
	 * @param type
	 *            The type of the new command.
	 */
	protected CommandBase(CommandType type) {
		super();
		this.type = type;
	}

	/**
	 * Gets the type of the command.
	 * 
	 * @return The type of the command.
	 */
	public CommandType getType() {
		return type;
	}

	/*
	 * Returns the string representation of the command. The string representation
	 * is also send over the network.
	 *
	 */
	@Override
	public String toString() {
		return type.toString();
	}
}
