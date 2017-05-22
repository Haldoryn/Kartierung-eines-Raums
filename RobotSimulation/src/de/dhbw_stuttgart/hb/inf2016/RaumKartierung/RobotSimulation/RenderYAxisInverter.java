package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.geom.Point2D;

public class RenderYAxisInverter {

	private int maxY = 1;

	public void calculateYAxis(Simulation sim,RenderScale scale) {
		maxY = 0;

		for (Point2D point : sim.getRoom().getOutline().getDefiningPoints()) {
			int yRounded = (int) Math.round(point.getY());
			if (yRounded > maxY) {
				maxY = yRounded;
			}
		}
		
		maxY= scale.scale(maxY);
	}

	public int invertY(int value) {
		int result = maxY-value;
		return result;
	}
}
