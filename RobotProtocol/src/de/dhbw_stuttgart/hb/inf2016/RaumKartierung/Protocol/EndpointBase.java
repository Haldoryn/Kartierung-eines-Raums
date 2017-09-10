package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandParser;

public abstract class EndpointBase implements IProtocolEndpoint {
	private List<ICommandReceiver> listeners = new LinkedList<>();
	private List<ICommandReceiver> sendInterceptor = new LinkedList<>();
	private Queue<CommandBase> commandQueue = new ConcurrentLinkedQueue<CommandBase>();
	private CommandParser parser;
	private IFromRobotSender robotInterface;
	private IToRobotSender controlerInterface;
	private List<IDisconnectedEventListener> disconnectedListeners = new LinkedList<>();

	protected EndpointBase() throws InstantiationException, IllegalAccessException {
		super();
		parser = new CommandParser();
		this.robotInterface = new FromRobotSender(this);
		this.controlerInterface = new ToRobotSender(this);
	}

	@Override
	public void addCommandListener(ICommandReceiver receiver) {
		if (receiver == null) {
			throw new IllegalArgumentException("The 'receiver' argument must not be null");
		}

		listeners.add(receiver);
	}

	public abstract void close();

	protected void doSendReceive(Socket sock) throws IOException {
		// Write all enqueued commands to the output stream.
		while (!commandQueue.isEmpty()) {
			CommandBase cmd = commandQueue.poll();
			byte[] data = cmd.toString().getBytes(StandardCharsets.US_ASCII);
			sock.getOutputStream().write(data);
			sock.getOutputStream().flush();
		}
		
		// Read all commands from the input stream.
		// Suppress the warning because we don't want to close the socket input
		// stream.
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(sock.getInputStream());
		sc.useDelimiter(";");
		while (sock.getInputStream().available()>0 &&sc.hasNext()) {
			try {
				CommandBase cmd = parser.Parse(sc.next());
				try
				{
					invokeHandlers(cmd);
				}
				catch(Exception ex)
				{
					System.err.println("A command handler caused an exception");
					ex.printStackTrace();
				}
				
			} catch (Exception e) {
				System.err.println("Something went wrong while receiving a command: ["+e.getClass().getName()+"]"+e.toString());
				e.printStackTrace();
			}
		}		
	}

	@Override
	public IToRobotSender getToRobotSender() {
		return controlerInterface;
	}

	@Override
	public IFromRobotSender getFromRobotSender() {
		return robotInterface;
	}

	private void invokeHandlers(CommandBase cmd) {
		for (ICommandReceiver listener : listeners) {
			listener.commandReceived(cmd);
		}
	}

	@Override
	public void removeCommandListener(ICommandReceiver receiver) {
		if (receiver == null) {
			throw new IllegalArgumentException("The 'receiver' argument must not be null");
		}
		listeners.remove(receiver);
	}

	@Override
	public void sendCommand(CommandBase cmd) {
		commandQueue.add(cmd);
		for(ICommandReceiver ic :sendInterceptor)
		{
			try
			{
				ic.commandReceived(cmd);
			}
			catch(Exception ex)
			{
				System.err.println("An command interceptor handler caused an exception.");
				ex.printStackTrace();
			}
		}
	}
	
	public void addOnDisconnectListener(IDisconnectedEventListener listener)
	{
		if (listener == null) {
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		disconnectedListeners.add(listener);
	}
	
	public void removeOnDisconnectListener(IDisconnectedEventListener listener)
	{
		if (listener == null) {
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		disconnectedListeners.remove(listener);
	}
	
	protected void callDisconnectEventHandlers()
	{
		for(IDisconnectedEventListener handler:disconnectedListeners)
		{
			handler.OnDisconnect();
		}
	}
	

	@Override
	public void addSendCommandInterceptorListener(ICommandReceiver handler) {
		if (handler == null) {
			throw new IllegalArgumentException("The 'handler' argument must not be null");
		}
		sendInterceptor.add(handler);
	}

	@Override
	public void removeSendCommandInterceptorListener(ICommandReceiver handler) {
		if (handler == null) {
			throw new IllegalArgumentException("The 'handler' argument must not be null");
		}
		sendInterceptor.remove(handler);
	}
}
