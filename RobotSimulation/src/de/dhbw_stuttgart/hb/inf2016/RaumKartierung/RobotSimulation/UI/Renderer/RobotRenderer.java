package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;

import javax.imageio.ImageIO;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Main;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.ImageConverter;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.ImageRotater;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderScale;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderYAxisInverter;

public class RobotRenderer implements SimulationRenderer {

	private Image robotImage;
	private RenderYAxisInverter inverter;
	private RenderScale scale;

	public RobotRenderer(RenderYAxisInverter inverter, RenderScale scale, Simulation simulation) throws IOException {
		super();

		if (inverter == null) {
			throw new InvalidParameterException("Parameter 'inverter' must not be null.");
		}
		if (scale == null) {
			throw new InvalidParameterException("Parameter 'scale' must not be null.");
		}

		this.inverter = inverter;
		this.scale = scale;

		int robotSize = scale.scale(simulation.getRobot().getSize());

		// Load robot image.
		URL resURL = Main.class.getResource("/SimRobot.png");
		robotImage = ImageIO.read(resURL).getScaledInstance(robotSize, robotSize, Image.SCALE_SMOOTH);
	}

	@Override
	public void render(Simulation sim, Graphics2D graphic) {

		if (sim == null) {
			throw new InvalidParameterException("Parameter 'sim' must not be null.");
		}
		if (graphic == null) {
			throw new InvalidParameterException("Parameter 'graphic' must not be null.");
		}

		Point2D position = inverter.invertYOfPoint(scale.scalePoint(sim.getRobot().getPosition()));

		// Convert to BufferedImage and rotate it to match the robot rotation.
		BufferedImage buffImage = ImageConverter.toBufferedImage(robotImage);
		buffImage = ImageRotater.RotateImage(buffImage, sim.getRobot().getRotation());
		int imagePosCenterX = (int) (position.getX() - robotImage.getWidth(null) / 2d);
		int imagePosCenterY = (int) (position.getY() - robotImage.getHeight(null) / 2d);

		graphic.drawImage(buffImage, imagePosCenterX, imagePosCenterY, null);
	}
}
