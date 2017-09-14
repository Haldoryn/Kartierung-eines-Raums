package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**The ReturnMessageCmd command.
 * @author JVogel
 *
 */
public class ReturnMessageCmd extends CommandBase {

	private String message = "";

	/**Initializes a new instance of the ReturnMessageCmd class.
	 * 
	 */
	public ReturnMessageCmd() {
		super(CommandType.returnMessage);
	}

	/**Initializes a new instance of the ReturnMessageCmd class.
	 * @param message The message that should be returned.
	 */
	public ReturnMessageCmd(String message) {
		super(CommandType.returnMessage);

		if (message == null) {
			throw new IllegalArgumentException("The 'message' parameter must not be null");
		}
		if (message.contains(";")) {
			throw new IllegalArgumentException("The 'message' parameter must not contain the termination symbol ';'");
		}
		this.message = message;
	}

	
	/**Gets the message.
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	/**Sets the message that should be returned.
	 * @param message The message that should be returned.
	 */
	public void setMessage(String message) {
		if (message == null) {
			throw new IllegalArgumentException("The 'message' parameter must not be null");
		}
		if (message.contains(";")) {
			throw new IllegalArgumentException("The 'message' parameter must not contain the termination symbol ';'");
		}
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase#toString()
	 */
	public String toString() {
		return super.toString() + "," + message + ";";
	}
}
