package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import java.util.List;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderScale;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderYAxisInverter;

public class LineShapeRenderer {
	RenderYAxisInverter inverter;
	RenderScale scale;
	
	public LineShapeRenderer(RenderYAxisInverter inverter, RenderScale scale) {
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

	// Draws lines between the points in the list.
	public void drawLinesBetweenPoints(Graphics2D g, List<Point2D> outlinePoints) {
		Point2D last = null;
		Point2D current = null;

		for (int i = 1; i < outlinePoints.size(); i++) {
			last = inverter.invertYOfPoint(scale.scalePoint(outlinePoints.get(i - 1)));
			current = inverter.invertYOfPoint(scale.scalePoint(outlinePoints.get(i)));
			g.drawLine((int) last.getX(), (int) last.getY(), (int) current.getX(), (int) current.getY());
		}

		// Draw line between first an last point.
		last = current;
		current = inverter.invertYOfPoint(scale.scalePoint(outlinePoints.get(0)));
		g.drawLine((int) last.getX(), (int) last.getY(), (int) current.getX(), (int) current.getY());
	}
}
