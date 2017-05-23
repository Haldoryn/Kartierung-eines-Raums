package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import java.awt.geom.Point2D;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulationData;

public class RenderYAxisInverter {

	private Point2D maxPoint = new Point2D.Double(1, 1);

	public void calculateYAxis(SimulationData sim, RenderScale scale) {
		maxPoint = new Point2D.Double(1, 1);

		for (Point2D point : sim.getRoom().getOutline().getDefiningPoints()) {
			int yRounded = (int) Math.round(point.getY());
			if (yRounded > maxPoint.getY()) {
				maxPoint = point;
			}
		}

		maxPoint = scale.scalePoint(maxPoint);
	}

	public Point2D invertYOfPoint(Point2D value) {
		return new Point2D.Double(value.getX(), maxPoint.getY() - value.getY());
	}

	public int invertY(double value) {
		return (int) (maxPoint.getY() - value);
	}
}
