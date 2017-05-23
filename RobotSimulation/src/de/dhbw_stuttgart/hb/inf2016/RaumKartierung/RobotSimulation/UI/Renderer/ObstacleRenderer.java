package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import java.util.List;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Obstacle;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderScale;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderYAxisInverter;

public class ObstacleRenderer implements SimulationRenderer {

	RenderYAxisInverter inverter;
	RenderScale scale;
	LineShapeRenderer lineRenderer;

	public ObstacleRenderer(RenderYAxisInverter inverter, RenderScale scale, LineShapeRenderer lineRenderer) {
		super();
		if (inverter == null) {
			throw new InvalidParameterException("Parameter 'inverter' must not be null.");
		}
		if (scale == null) {
			throw new InvalidParameterException("Parameter 'scale' must not be null.");
		}
		if (lineRenderer == null) {
			throw new InvalidParameterException("Parameter 'lineRenderer' must not be null.");
		}

		this.inverter = inverter;
		this.scale = scale;
		this.lineRenderer = lineRenderer;
	}

	@Override
	public void render(Simulation sim, Graphics2D graphic) {

		if (sim == null) {
			throw new InvalidParameterException("Parameter 'sim' must not be null.");
		}
		if (graphic == null) {
			throw new InvalidParameterException("Parameter 'graphic' must not be null.");
		}

		for (Obstacle obstacle : sim.getRoom().getObstacles()) {

			List<Point2D> obstaclePoints = obstacle.getDefiningPoints();

			// Draw the lines of the obstacles
			graphic.setColor(Color.blue);
			lineRenderer.drawLinesBetweenPoints(graphic, obstacle.getDefiningPoints());

			// Draw the name text of the obstacle.
			graphic.setColor(Color.green);
			graphic.setFont(new Font("Arial", Font.PLAIN, 20));
			Point2D current = inverter.invertYOfPoint(scale.scalePoint(obstaclePoints.get(0)));
			graphic.drawString(obstacle.getName(), (int) current.getX(), (int) current.getY());
		}
	}
}
