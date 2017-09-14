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
	
	//The connection socket.
	Socket connection;
	
	//Stores the IConnectionIncommingEventHandler listners.
	private List<IConnectionIncommingEventHandler> connectionIncommingHandler = new LinkedList<>();

	/**Initializes a new instance of the RobotEndpoint class.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public RobotEndpoint() throws InstantiationException, IllegalAccessException {
		super();
	}

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.EndpointBase#close()
	 */
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

	/* (non-Javadoc)
	 * @see de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IProtocolEndpoint#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return !serverSocket.isBound() && !serverSocket.isClosed();
	}

	/**Opens the endpoint.
	 * @param port The port at which the endpoint should be opened.
	 * @throws IOException Thrown if the port can not be opened
	 */
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
	
	/**Adds a listener that informs about incoming connections.
	 * @param listener The listener that should be added.
	 */
	public void addOnConnectionIncommingListener(IConnectionIncommingEventHandler listener)
	{
		if (listener == null) {
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		connectionIncommingHandler.add(listener);
	}
	
	/**Removes a OnConnectionIncommingListener.
	 * @param listener The listner that should be removed.
	 */
	public void removeOnConnectionIncommingListener(IConnectionIncommingEventHandler listener)
	{
		if (listener == null) {
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		connectionIncommingHandler.remove(listener);
	}
}
