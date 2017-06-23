package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulationData;
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
	private SimulationData currentData;

	RenderScale scale = new RenderScale(imageSize);
	RenderYAxisInverter inverter = new RenderYAxisInverter();

	private final ArrayList<SimulationRenderer> renderers = new ArrayList<>();

	// Override the paint method of the JPanel to render the simulation.
	@Override
	public void paint(java.awt.Graphics g) {
		if (currentData == null)
			return;
		super.paintComponent(g);

		// Render the simulation of an additional image.
		BufferedImage img = new BufferedImage(imageSize + 1, imageSize + 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphic = img.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Invoke the renderers.
		for (SimulationRenderer renderer : renderers) {
			renderer.render(currentData, graphic);
		}
		// Scale the rendered image to match the size of the Panel.
		g.drawImage(img.getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH), 10, 10, null);
	}

	/**
	 * Initializes a new instance of the {@link SimulationPanel} class.
	 * 
	 * @param simulation
	 *            The {@link SimulationData} that should be displayed.
	 * @throws IOException
	 */
	public SimulationPanel() throws IOException {
		super();

		// Initialize the renderers
		LineShapeRenderer lineRenderer = new LineShapeRenderer(inverter, scale);
		renderers.add(new ObstacleRenderer(inverter, scale, lineRenderer));
		renderers.add(new OutlineRenderer(inverter, scale, lineRenderer));
		renderers.add(new SensorRotationRenderer(inverter, scale));
		renderers.add(new RobotRenderer(inverter, scale));
	};

	public void setSimulationData(SimulationData simData) {
		if(simData==null)
		{
			throw new IllegalArgumentException("Argument 'simData' must not be null.");
		}		
		// Initialize the calculation classes.
		scale.calculateScale(simData);
		inverter.calculateYAxis(simData, scale);

		currentData = simData;
		this.repaint();
	}
}
