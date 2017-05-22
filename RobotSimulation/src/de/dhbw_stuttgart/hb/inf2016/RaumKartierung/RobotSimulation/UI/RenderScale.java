package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import java.awt.geom.Point2D;
import java.security.InvalidParameterException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;

public class RenderScale {
	private double currentFactor=1;
	private double maxSize = 1000;
	
	
	
	public RenderScale(double maxSize) {
		super();
		this.maxSize = maxSize;
	}

	void calculateScale(Simulation sim) {
		if (sim == null) {
			throw new InvalidParameterException("sim parameter must not be null");
		}

		int maxX = 0;
		int maxY = 0;

		for (Point2D point : sim.getRoom().getOutline().getDefiningPoints()) {
			int xRounded = (int) Math.round(point.getX());
			int yRounded = (int) Math.round(point.getY());
			if (xRounded > maxX) {
				maxX = xRounded;
			}
			if (yRounded > maxY) {
				maxY = yRounded;
			}
		}
		double xScale = maxSize/maxX;
		double yScale = maxSize/maxY;
		
		if(xScale < yScale)
		{
			currentFactor=xScale;
		}
		else
		{
			currentFactor=yScale;
		}	
	}
	
	public Point2D scale(Point2D value)
	{
		return new Point2D.Double(value.getX()*currentFactor,value.getY()*currentFactor);
	}
}
