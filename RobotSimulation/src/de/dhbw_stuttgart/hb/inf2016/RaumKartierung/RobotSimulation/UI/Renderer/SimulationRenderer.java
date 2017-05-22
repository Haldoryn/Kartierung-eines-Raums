package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.Renderer;

import java.awt.Graphics2D;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;


/**Interface for renderer classes of the robot simulation ui.
 * @author Julian Vogel
 *
 */
public interface SimulationRenderer {
	
	/**Renders a part of the {@link Simulation} to a {@link Graphics2D}.
	 * @param sim The {@link Simulation} that should be rendered.
	 * @param graphic The {@link Graphics2D} on which the simulation should be rendered.
	 */
	public void render(Simulation sim, Graphics2D graphic);
}
