package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.ProtocolConsole;

import java.net.InetAddress;
import java.util.Scanner;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ControlerEndpoint;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ICommandReceiver;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandParser;

/**
 * Small tool for manually sending commands to the robot.
 * 
 * @author JVogel
 *
 */
public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		System.out.println("Starting ProtocolConsole...");

		Scanner in = new Scanner(System.in);

		// Create a parser for parsing robot commands
		CommandParser parser = new CommandParser();
		System.out.println("Parser creation successfull");

		// Create a endpoint and connect it to the robot.
		ControlerEndpoint endpoint = new ControlerEndpoint();
		endpoint.addCommandListener(new ICommandReceiver() {

			@Override
			public void commandReceived(CommandBase cmd) {
				System.out.println("Response from robot: " + cmd.toString());
			}
		});
		try {
			System.out.println("Trying to connect to the robot");
			endpoint.connect(InetAddress.getByName("10.0.1.1"), 9876);
			System.out.println("Connection successfully estabished");
		} catch (Exception e) {
			System.out.println("Could not connect to to robot: " + e);
		}
		System.out.println("Awaiting command input");
		while (true) {
			try {
				String input = in.nextLine();
				if(input.equals("exit"))
				{
					break;
				}
				
				CommandBase cmd = parser.Parse(input);
				System.out.println("Command parsed: "+cmd.toString()); 
				
				endpoint.sendCommand(cmd);
				System.out.println("Command send.");
				
			} catch (Exception ex) {
				System.out.println("Could not parse command");
			}
		}
		endpoint.close();
	}

}
