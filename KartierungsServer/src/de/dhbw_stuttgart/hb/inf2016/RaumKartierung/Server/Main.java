package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Controlling;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.IConnectEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.IStartEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.IStopEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.MainWindow;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;

import java.awt.Point;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.omg.PortableServer.ServantActivator;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.*;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandType;

/**
 * 
 * @author Samuel Volz
 *
 */

public class Main {

	private static RobotInteractionHandler robotInteractionHandler;
	private static boolean isRunning;
	private static MainWindow window;
	private static Thread workThread;

	/**
	 * main is the starting class of this program. It calls the GUI and goes thru
	 * the procedure of the robot.
	 * 
	 * @param commandline
	 *            args. we do not use them.
	 */
	public static void main(String[] args) {
		/*
		 * first up the main needs to start the GUI. It has to wait till the GUI
		 * responds with informations on how to proceed. If the start button was
		 * pressed, the main continues with the procedure.
		 */
		window = new MainWindow();
		window.Show();
		window.addOnStartEventListener(new IStartEventListener() {

			@Override
			public void onStart() {
				start();

			}
		});
		window.addOnStopEventListener(new IStopEventListener() {

			@Override
			public void onStop() {
				stop();

			}
		});
		window.addOnConnectEventListener(new IConnectEventListener() {

			@Override
			public void onConnect(InetAddress ip, int port) {
				// TODO Auto-generated method stub

				ControlerEndpoint endpoint = null;
				try {
					endpoint = new ControlerEndpoint();
					endpoint.addCommandListener(new ICommandReceiver() {

						@Override
						public void commandReceived(CommandBase cmd) {
							window.addLogEntry(cmd.toString());
						}
					});
					endpoint.addSendCommandInterceptorListener(new ICommandReceiver() {

						@Override
						public void commandReceived(CommandBase cmd) {
							window.addLogEntry(cmd.toString());
						}
					});

					endpoint.connect(ip, port);
					robotInteractionHandler = new RobotInteractionHandler(endpoint, new Config());

				} catch (Exception e) {
					if (endpoint != null) {
						endpoint.close();
						robotInteractionHandler = null;
					}
					JOptionPane.showMessageDialog(null,
							"Could not connect to the robot\n[" + e.getClass().getName() + "]" + e.getMessage(),
							"Connection Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	/**
	 * The start method gets called, if the start button in the GUI gets clicked. It
	 * starts the interaction procedure.
	 */
	public static void start() {
		if (robotInteractionHandler == null) {
			JOptionPane.showMessageDialog(null, "Not connected to the robot", "Connection Error",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (isRunning == false) {
			isRunning = true;

			workThread = new Thread(new Runnable() {
				@Override
				public void run() {
					excuteCartoLogic();

				}
			});
			
			workThread.setName("Work thread");
			workThread.setDaemon(true);
			workThread.start();
		}

	}

	/**
	 * The stop method gets called, if the stop button in the GUI gets clicked. It
	 * stops the interaction procedure.
	 */
	public static void stop() {
		isRunning = false;
	}

	/**
	 * The run method calls the doMove method in a loop till isRunning == false.
	 */
	public static void excuteCartoLogic() {
		while (isRunning) {
			try {
				robotInteractionHandler.doMove();
				window.clearPoints();
				for (double[] point : robotInteractionHandler.getVectorRoom().getPointsPositivOnly()) {
					window.addPoint(new Point((int) point[0], (int) point[1]));
				}
				window.repaintImage();

			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "System was interupted", "System interrupt",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		// Stuff to do if the robot should stop running.
	}
}