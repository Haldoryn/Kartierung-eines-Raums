package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class SimulationPanel extends JPanel {

	private Simulation simulation;
	
	@Override
	public void paint(java.awt.Graphics g) {
		if(simulation== null)
			return;
		
		
		g.drawImage(paintOutline(simulation).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 10, 10,null);
	}
	
	
	public static Image paintOutline(Simulation sim)
	{ 
		BufferedImage img = new BufferedImage(500, 500, 
            BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = img.createGraphics();
		g.setBackground(Color.white);
		g.clearRect(0, 0, 500, 500);
		g.setColor(Color.red);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		List<Point2D> outlinePoints = sim.getRoom().getOutline().getDefiningPoints();
		
		for(int i =1; i< outlinePoints.size();i++)
		{
			Point2D last = outlinePoints.get(i-1);
			Point2D current = outlinePoints.get(i);
			
			g.drawLine((int)last.getX(), (int)last.getY(),(int)current.getX(), (int)current.getY());
		}
		Point2D last = outlinePoints.get(outlinePoints.size()-1);
		Point2D current = outlinePoints.get(0);
		
		g.drawLine((int)last.getX(), (int)last.getY(),(int)current.getX(), (int)current.getY());
		
		
		return  img ;
	}

	public SimulationPanel(Simulation simulation) {
		super();
		this.simulation = simulation;
	};
}
