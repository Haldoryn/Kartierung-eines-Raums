package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.net.ServerSocket;

public class RobotEndpoint extends EndpointBase {

	public RobotEndpoint() throws InstantiationException, IllegalAccessException {
		super();
	}

	private ServerSocket socket;

	private boolean run;

	public void open(int port) throws IOException {
		socket = new ServerSocket(port);
		run = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (run) {
					try {
						doSendReceive(socket.accept());

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("An socket error occured.");
					}
				}
			}
		});
	}

	@Override
	public boolean isConnected() {
		return !socket.isBound() && !socket.isClosed();
	}
}
