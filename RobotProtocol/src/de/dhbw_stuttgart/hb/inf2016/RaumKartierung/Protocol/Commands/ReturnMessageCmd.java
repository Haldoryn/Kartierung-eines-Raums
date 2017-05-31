package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

public class ReturnMessageCmd extends CommandBase {

	private String message = "";

	public ReturnMessageCmd() {
		super(CommandType.returnMessage);
	}

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if (message == null) {
			throw new IllegalArgumentException("The 'message' parameter must not be null");
		}
		if (message.contains(";")) {
			throw new IllegalArgumentException("The 'message' parameter must not contain the termination symbol ';'");
		}
		this.message = message;
	}

	public String toString() {
		return super.toString() + "," + message + ";";
	}
}
