package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**Protocol endpoint class of the robot.
 * @author Julian Vogel
 *
 */
public class RobotEndpoint extends EndpointBase {

	/**
	 * The sever socket used to accept connections from a controller.
	 * 
	 */
	private ServerSocket serverSocket;
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
	
	Socket connection;
	
	private List<IConnectionIncommingEventHandler> connectionIncommingHandler = new LinkedList<>();

	public RobotEndpoint() throws InstantiationException, IllegalAccessException {
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

	@Override
	public boolean isConnected() {
		return !serverSocket.isBound() && !serverSocket.isClosed();
	}

	public void open(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		run = true;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (run) {
					try {
						if(connection!= null && connection.isConnected())
						{
							doSendReceive(connection);
						}
						else if (connection!= null && !connection.isConnected())
						{
							callDisconnectEventHandlers();
							connection=null;
						}					
						else
						{
							connection= serverSocket.accept();
							for(IConnectionIncommingEventHandler handler: connectionIncommingHandler){
								handler.OnNewConnection(connection);
							}
						}		
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
	}
	
	public void addOnConnectionIncommingListener(IConnectionIncommingEventHandler listener)
	{
		if (listener == null) {
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		connectionIncommingHandler.add(listener);
	}
	
	public void removeOnConnectionIncommingListener(IConnectionIncommingEventHandler listener)
	{
		if (listener == null) {
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		connectionIncommingHandler.remove(listener);
	}
}
