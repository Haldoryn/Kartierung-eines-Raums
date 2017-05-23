package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.security.InvalidParameterException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulationData;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderScale;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderYAxisInverter;


/**Renderer for the current position of the ultrasonic sensor.
 * @author Julian Vogel
 *
 */
public class SensorRotationRenderer implements SimulationRenderer {

	private RenderYAxisInverter inverter;
	private RenderScale scale;

	public SensorRotationRenderer(RenderYAxisInverter inverter, RenderScale scale) throws IOException {
		super();

		if (inverter == null) {
			throw new InvalidParameterException("Parameter 'inverter' must not be null.");
		}
		if (scale == null) {
			throw new InvalidParameterException("Parameter 'scale' must not be null.");
		}

		this.inverter = inverter;
		this.scale = scale;
	}

	@Override
	public void render(SimulationData sim, Graphics2D graphic) {
		int sensorRotation = 90 - sim.getRobot().getSensorRotation();

		// Calculate the center of the robot
		Point2D positionStart = sim.getRobot().getPosition();

		// Calculate line end point. Point on circle around the robot.
		int pointX = (int) (sim.getRobot().getSize() * 2 * Math.cos(Math.toRadians(sensorRotation))
				+ positionStart.getX());
		int pointY = (int) (sim.getRobot().getSize() * 2 * Math.sin(Math.toRadians(sensorRotation))
				+ positionStart.getY());

		// Adapt the start and end point to the rendering coordinates.
		Point2D pointEnd = inverter.invertYOfPoint(scale.scalePoint(new Point2D.Double(pointX, pointY)));
		positionStart = inverter.invertYOfPoint(scale.scalePoint(positionStart));

		// Draw the line
		graphic.setColor(Color.GREEN);
		graphic.drawLine((int) positionStart.getX(), (int) positionStart.getY(), (int) pointEnd.getX(),
				(int) pointEnd.getY());
	}
}
