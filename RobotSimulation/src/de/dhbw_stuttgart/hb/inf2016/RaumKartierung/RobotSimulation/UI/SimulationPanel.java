package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Obstacle;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;


/**
 * A extension of {@link JPanel} that renders the simulation.
 * 
 * @author Julian Vogel
 *
 */
public class SimulationPanel extends JPanel {

	private final int imageSize = 1000;
	private Simulation simulation;
	private RenderScale scale = new RenderScale(imageSize);
	private RenderYAxisInverter inverter = new RenderYAxisInverter();

	//Override the paint method of the JPanel to render the simulation.
	@Override
	public void paint(java.awt.Graphics g) {
		if (simulation == null)
			return;

		//Render the simulation of an additional image.
		BufferedImage img = new BufferedImage(imageSize + 1, imageSize + 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphic = img.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintOutline(simulation, graphic);
		paintObstacles(simulation, graphic);

		//Scale the rendered image to match the size of the Panel.
		g.drawImage(img.getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH), 10, 10, null);
	}

	public void paintOutline(Simulation sim, Graphics2D graphic) {
		//Draw the lines of the room outline.
		graphic.setColor(Color.red);
		drawLinesBetweenPoints(graphic, sim.getRoom().getOutline().getDefiningPoints());
	}

	//Draws the obstacles of the simulation
	public void paintObstacles(Simulation sim, Graphics2D g) {

		for (Obstacle obstacle : sim.getRoom().getObstacles()) {

			List<Point2D> obstaclePoints = obstacle.getDefiningPoints();

			//Draw the lines of the obstacles
			g.setColor(Color.blue);			
			drawLinesBetweenPoints(g, obstacle.getDefiningPoints());

			//Draw the name text of the obstacle.
			g.setColor(Color.green);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			Point2D current = inverter.invertY(scale.scale(obstaclePoints.get(0)));
			g.drawString(obstacle.getName(), (int) current.getX(), (int) current.getY());
		}
	}

	
	//Draws lines between the points in the list.
	private void drawLinesBetweenPoints(Graphics2D g, List<Point2D> outlinePoints) {
		Point2D last = null;
		Point2D current = null;

		for (int i = 1; i < outlinePoints.size(); i++) {
			last = inverter.invertY(scale.scale(outlinePoints.get(i - 1)));
			current = inverter.invertY(scale.scale(outlinePoints.get(i)));
			g.drawLine((int) last.getX(), (int) last.getY(), (int) current.getX(), (int) current.getY());
		}
		
		//Draw line between first an last point.
		last = current;
		current = inverter.invertY(scale.scale(outlinePoints.get(0)));
		g.drawLine((int) last.getX(), (int) last.getY(), (int) current.getX(), (int) current.getY());
	}

	
	/**Initializes a new instance of the {@link SimulationPanel} class.
	 * @param simulation The {@link Simulation} that should be displayed.
	 */
	public SimulationPanel(Simulation simulation) {
		super();
		this.simulation = simulation;
		scale.calculateScale(simulation);
		inverter.calculateYAxis(simulation, scale);
	};
}