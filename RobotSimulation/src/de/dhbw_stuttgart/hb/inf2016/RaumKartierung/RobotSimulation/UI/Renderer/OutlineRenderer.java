package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.security.InvalidParameterException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderScale;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.RenderYAxisInverter;

public class OutlineRenderer implements SimulationRenderer {
	RenderYAxisInverter inverter;
	RenderScale scale;
	LineShapeRenderer lineRenderer;

	public OutlineRenderer(RenderYAxisInverter inverter, RenderScale scale, LineShapeRenderer lineRenderer) {
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

		// Draw the lines of the room outline.
		graphic.setColor(Color.red);
		lineRenderer.drawLinesBetweenPoints(graphic, sim.getRoom().getOutline().getDefiningPoints());
	}
}
