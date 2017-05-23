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
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Main;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Obstacle;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer.LineShapeRenderer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer.ObstacleRenderer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer.OutlineRenderer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer.RobotRenderer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer.SensorRotationRenderer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer.SimulationRenderer;

/**
 * A extension of {@link JPanel} that renders the simulation.
 * 
 * @author Julian Vogel
 *
 */
public class SimulationPanel extends JPanel {

	private final int imageSize = 1000;
	private Simulation simulation;

	private final ArrayList<SimulationRenderer> renderers = new ArrayList<>();

	// Override the paint method of the JPanel to render the simulation.
	@Override
	public void paint(java.awt.Graphics g) {
		if (simulation == null)
			return;

		// Render the simulation of an additional image.
		BufferedImage img = new BufferedImage(imageSize + 1, imageSize + 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphic = img.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Invoke the renderers.
		for (SimulationRenderer renderer : renderers) {
			renderer.render(simulation, graphic);
		}
		// Scale the rendered image to match the size of the Panel.
		g.drawImage(img.getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH), 10, 10, null);
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

		if (simulation == null) {
			throw new InvalidParameterException("Parameter 'simulation' must not be null.");
		}

		this.simulation = simulation;

		// Initialize the calculation classes.
		RenderScale scale = new RenderScale(imageSize);
		RenderYAxisInverter inverter = new RenderYAxisInverter();
		scale.calculateScale(simulation);
		inverter.calculateYAxis(simulation, scale);

		// Initialize the renderers
		LineShapeRenderer lineRenderer = new LineShapeRenderer(inverter, scale);
		renderers.add(new ObstacleRenderer(inverter, scale, lineRenderer));
		renderers.add(new OutlineRenderer(inverter, scale, lineRenderer));
		renderers.add(new SensorRotationRenderer(inverter, scale));
		renderers.add(new RobotRenderer(inverter, scale, simulation));
	};
}
