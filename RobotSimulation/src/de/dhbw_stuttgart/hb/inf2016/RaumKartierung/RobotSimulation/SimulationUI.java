package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SimulationUI {

	private JFrame frame;
	/**
	 * Create the application.
	 */
	public SimulationUI() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}

}
