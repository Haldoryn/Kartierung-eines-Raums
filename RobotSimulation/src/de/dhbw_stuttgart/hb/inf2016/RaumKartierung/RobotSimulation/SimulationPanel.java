package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class SimulationPanel extends JPanel {

	private final int imageSize = 1000;
	private Simulation simulation;
	private RenderScale scale = new RenderScale(imageSize);

	@Override
	public void paint(java.awt.Graphics g) {
		if (simulation == null)
			return;

		g.drawImage(paintOutline(simulation).getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH),
				10, 10, null);
		g.drawImage(paintObstacles(simulation).getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH),
				10, 10, null);
	}

	public Image paintOutline(Simulation sim) {
		BufferedImage img = new BufferedImage(imageSize + 1, imageSize + 1, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = img.createGraphics();
		g.setColor(Color.red);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		List<Point2D> outlinePoints = sim.getRoom().getOutline().getDefiningPoints();

		for (int i = 1; i < outlinePoints.size(); i++) {
			Point2D last = outlinePoints.get(i - 1);
			Point2D current = outlinePoints.get(i);

			g.drawLine(scale.scale(last.getX()), scale.scale(last.getY()), scale.scale(current.getX()),
					scale.scale(current.getY()));
		}
		Point2D last = outlinePoints.get(outlinePoints.size() - 1);
		Point2D current = outlinePoints.get(0);

		g.drawLine(scale.scale(last.getX()), scale.scale(last.getY()), scale.scale(current.getX()),
				scale.scale(current.getY()));

		return img;
	}

	public Image paintObstacles(Simulation sim) {
		BufferedImage img = new BufferedImage(imageSize + 1, imageSize + 1, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = img.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (Obstacle obstacle : sim.getRoom().getObstacles()) {
			g.setColor(Color.blue);
			
			List<Point2D> outlinePoints = obstacle.getDefiningPoints();

			for (int i = 1; i < outlinePoints.size(); i++) {
				Point2D last = outlinePoints.get(i - 1);
				Point2D current = outlinePoints.get(i);

				g.drawLine(scale.scale(last.getX()), scale.scale(last.getY()), scale.scale(current.getX()),
						scale.scale(current.getY()));
			}
			Point2D last = outlinePoints.get(outlinePoints.size() - 1);
			Point2D current = outlinePoints.get(0);

			g.drawLine(scale.scale(last.getX()), scale.scale(last.getY()), scale.scale(current.getX()),
					scale.scale(current.getY()));
			
			g.setColor(Color.green);
			g.setFont(new Font("Arial",Font.PLAIN,20));
			g.drawString(obstacle.getName(), scale.scale(last.getX()), scale.scale(last.getY()));
		}

		return img;
	}

	public SimulationPanel(Simulation simulation) {
		super();
		this.simulation = simulation;
		scale.calculateScale(simulation);
	};
}
