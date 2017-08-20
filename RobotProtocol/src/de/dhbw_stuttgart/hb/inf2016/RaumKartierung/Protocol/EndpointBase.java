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
	private Queue<CommandBase> commandQueue = new ConcurrentLinkedQueue<CommandBase>();
	private CommandParser parser;
	private IRobot robotInterface;
	private IControler controlerInterface;
	private List<IDisconnectedEventListener> disconnectedListeners = new LinkedList<>();

	protected EndpointBase() throws InstantiationException, IllegalAccessException {
		super();
		parser = new CommandParser();
		this.robotInterface = new Robot(this);
		this.controlerInterface = new Controler(this);
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
				invokeHandlers(cmd);
			} catch (Exception e) {
				System.out.println("Something went wrong while receiving a command");
			}
		}		
	}

	@Override
	public IControler getControlerInterface() {
		return controlerInterface;
	}

	@Override
	public IRobot getRobotInterface() {
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
}
