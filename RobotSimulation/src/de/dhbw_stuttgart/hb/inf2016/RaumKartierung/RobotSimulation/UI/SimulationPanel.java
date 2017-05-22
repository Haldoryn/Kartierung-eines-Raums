package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Main;
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
	private Image robotImage;

	// Override the paint method of the JPanel to render the simulation.
	@Override
	public void paint(java.awt.Graphics g) {
		if (simulation == null)
			return;

		// Render the simulation of an additional image.
		BufferedImage img = new BufferedImage(imageSize + 1, imageSize + 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphic = img.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintOutline(simulation, graphic);
		paintObstacles(simulation, graphic);
		paintRobot(simulation, graphic);

		// Scale the rendered image to match the size of the Panel.
		g.drawImage(img.getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH), 10, 10, null);
	}

	public void paintRobot(Simulation sim, Graphics2D graphic) {
		// Draw the lines of the room outline.
		Point2D position = inverter.invertY(scale.scalePoint(sim.getRobot().getPosition()));
		
		
		double rot = sim.getRobot().getRotation();
		double rotationRequired = Math.toRadians (rot);
		double locationX = robotImage.getWidth(null) / 2;
		double locationY = robotImage.getHeight(null) / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		graphic.drawImage(op.filter(toBufferedImage(robotImage),null),(int)(position.getX()+robotImage.getWidth(null)/2d) , (int)(position.getY()+robotImage.getHeight(null)/2d), null);
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

	public void paintOutline(Simulation sim, Graphics2D graphic) {
		// Draw the lines of the room outline.
		graphic.setColor(Color.red);
		drawLinesBetweenPoints(graphic, sim.getRoom().getOutline().getDefiningPoints());
	}

	// Draws the obstacles of the simulation
	public void paintObstacles(Simulation sim, Graphics2D g) {

		for (Obstacle obstacle : sim.getRoom().getObstacles()) {

			List<Point2D> obstaclePoints = obstacle.getDefiningPoints();

			// Draw the lines of the obstacles
			g.setColor(Color.blue);
			drawLinesBetweenPoints(g, obstacle.getDefiningPoints());

			// Draw the name text of the obstacle.
			g.setColor(Color.green);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			Point2D current = inverter.invertY(scale.scalePoint(obstaclePoints.get(0)));
			g.drawString(obstacle.getName(), (int) current.getX(), (int) current.getY());
		}
	}

	// Draws lines between the points in the list.
	private void drawLinesBetweenPoints(Graphics2D g, List<Point2D> outlinePoints) {
		Point2D last = null;
		Point2D current = null;

		for (int i = 1; i < outlinePoints.size(); i++) {
			last = inverter.invertY(scale.scalePoint(outlinePoints.get(i - 1)));
			current = inverter.invertY(scale.scalePoint(outlinePoints.get(i)));
			g.drawLine((int) last.getX(), (int) last.getY(), (int) current.getX(), (int) current.getY());
		}

		// Draw line between first an last point.
		last = current;
		current = inverter.invertY(scale.scalePoint(outlinePoints.get(0)));
		g.drawLine((int) last.getX(), (int) last.getY(), (int) current.getX(), (int) current.getY());
	}

	/**
	 * Initializes a new instance of the {@link SimulationPanel} class.
	 * 
	 * @param simulation
	 *            The {@link Simulation} that should be displayed.
	 * @throws IOException
	 */
	public SimulationPanel(Simulation simulation) throws IOException {
		super();
		this.simulation = simulation;
		scale.calculateScale(simulation);
		inverter.calculateYAxis(simulation, scale);
		
		int robotSize = scale.scale(simulation.getRobot().getSize());

		// Load robot image.
		URL resURL = Main.class.getResource("/SimRobot.png");
		robotImage = ImageIO.read(resURL).getScaledInstance(robotSize,robotSize, Image.SCALE_SMOOTH);
	};
}
