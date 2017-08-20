package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**Protocol endpoint class of the controller server.
 * @author Julian Vogel
 *
 */
public class ControlerEndpoint extends EndpointBase {

	/**
	 * The default timeout for connecting to the 'sever socket'.
	 * 
	 */
	private static final int TIMEOUT = 5000;

	/**
	 * A value indicating whether the background sending thread should continue.
	 * 
	 */
	private boolean run;

	/**
	 * The background thread that is used to send end receive protocol data.
	 * 
	 */
	Thread thread;

	public ControlerEndpoint() throws InstantiationException, IllegalAccessException {
		super();
	}

	@Override
	public void close() {
		if (thread != null && thread.isAlive()) {
			run = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println("Exception controller endpoint close.");
			}
		}
	}

	public void connect(InetAddress ip, int port) throws IOException {
		final Socket sock = new Socket();
		sock.connect(new InetSocketAddress(ip, port), TIMEOUT);
		run=true;

		// Connection successful. start the network thread.
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Controller enpoint thread started");
				while (run) {
					try {
						doSendReceive(sock);
						Thread.sleep(10);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("An socket error occured.");
					}				
				}
			}
		});
		thread.setDaemon(true);
		
		thread.start();
		System.out.println("SenderReceiver Thread Started");
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

}
