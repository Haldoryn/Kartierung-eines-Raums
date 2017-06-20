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
	private IControler server;

	@Override
	public void sendCommand(CommandBase cmd) {
		commandQueue.add(cmd);
	}

	protected EndpointBase() throws InstantiationException, IllegalAccessException {
		super();
		parser = new CommandParser();
		server = new Controler(this);
	}

	protected void doSendReceive(Socket sock) throws IOException {

		// Write all enqueued commands to the output stream.
		while (!commandQueue.isEmpty()) {
			CommandBase cmd = commandQueue.poll();
			byte[] data = cmd.toString().getBytes(StandardCharsets.US_ASCII);
			sock.getOutputStream().write(data);
		}
		// Flush the stream after all data was written.
		sock.getOutputStream().flush();

		// Read all commands from the input stream.
		Scanner sc = new Scanner(sock.getInputStream());
		sc.useDelimiter(";");
		while (sc.hasNextLine()) {
			try {
				invokeHandlers(parser.Parse(sc.nextLine()));
			} catch (Exception e) {
				System.out.println("Something went wrong while receiving a command");
			}
		}
	}

	private void invokeHandlers(CommandBase cmd) {
		for (ICommandReceiver listener : listeners) {
			listener.commandReceived(cmd);
		}
	}

	@Override
	public void addCommandListener(ICommandReceiver receiver) {
		if (receiver == null) {
			throw new IllegalArgumentException("The 'receiver' argument must not be null");
		}

		listeners.add(receiver);
	}

	@Override
	public void removeCommandListener(ICommandReceiver receiver) {
		if (receiver == null) {
			throw new IllegalArgumentException("The 'receiver' argument must not be null");
		}
		listeners.remove(receiver);
	}

	@Override
	public IRobot getRobotInterface() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IControler getControlerInterface() {
		return server;
	}
}
