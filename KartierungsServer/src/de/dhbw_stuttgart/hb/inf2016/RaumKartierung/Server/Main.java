package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.List;

import javax.activity.InvalidActivityException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ControlerEndpoint;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ICommandReceiver;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.IConnectEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.ISaveEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.IStartEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.IStopEventListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.MainWindow;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Vector;

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
	private static BufferedImage lastImage;

	/**
	 * main is the starting class of this program. It calls the GUI and goes thru
	 * the procedure of the robot.
	 * 
	 * @param commandline
	 *            args. we do not use them.
	 * @throws ParseException
	 * @throws InvalidActivityException
	 */
	public static void main(String[] args) throws InvalidActivityException, ParseException {

		Config config = new Config();

		/*
		 * first up the main needs to start the GUI. It has to wait till the GUI
		 * responds with informations on how to proceed. If the start button was
		 * pressed, the main continues with the procedure.
		 */
		window = new MainWindow();
		window.show();
		window.addOnSaveEventListener(new ISaveEventListener() {

			@Override
			public void onSave() {
				if (lastImage == null)
					return;

				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Wählen Sie den Ordner zum Speichern der Kartierung");
				chooser.showSaveDialog(null);
				File file = chooser.getSelectedFile();
				if (file != null) {
					try {
						ImageIO.write(lastImage, "jpg", file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Could not save image", "File save error",
								JOptionPane.INFORMATION_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});

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
					robotInteractionHandler = new RobotInteractionHandler(endpoint, config);

				} catch (Exception e) {
					if (endpoint != null) {
						endpoint.close();
						robotInteractionHandler = null;
					}
					JOptionPane.showMessageDialog(null,
							"Could not connect to the robot\n[" + e.getClass().getName() + "]" + e.getMessage(),
							"Connection Error", JOptionPane.INFORMATION_MESSAGE);
					e.printStackTrace();
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
				lastImage = createGridMap(robotInteractionHandler.getVectorRoom().getPointsPositivOnly(),robotInteractionHandler.getRobot().getVector());
				if (lastImage != null) {
					window.setImage(lastImage);
					window.repaintImage();
				}
				window.setPositionText("Pos: "+robotInteractionHandler.getRobot().toString());

			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "System was interupted", "System interrupt",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private static BufferedImage createGridMap(List<Vector> points,Vector roboPos) {
		if (points.size() == 0)
			return null;

		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;

		for (Vector point : points) {
			if (point.getX() < minX)
				minX = (float) point.getX();
			if (point.getY() < minY)
				minY = (float) point.getY();
			if (point.getX() > maxX)
				maxX = (float) point.getX();
			if (point.getY() > maxY)
				maxY = (float) point.getY();
		}
		
		BufferedImage img = new BufferedImage(Math.round(maxX), Math.round(maxX),BufferedImage.TYPE_INT_RGB);
		Graphics2D g =(Graphics2D) img.getGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.RED);
		
		for (Vector point : points) {
			g.fillRect(Math.round((int)point.getX()),(int) Math.round(point.getY()), 1, 1);
		}
		
		g.setColor(Color.BLUE);
		g.drawRect((int)Math.round(roboPos.getX()),(int) Math.round(roboPos.getY()), 1, 1);

		return img;
	}
}