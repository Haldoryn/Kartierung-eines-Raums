package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.BoxLayout;

public class SimulationUI {
	
	private Simulation simulation;

	private JFrame frame;
	/**
	 * Create the application.
	 */
	public SimulationUI(Simulation sim) {
		this.simulation=sim;
		initialize();
		this.frame.setVisible(true);		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 387);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
			SimulationPanel simPanel = new SimulationPanel(simulation);
			simPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			frame.getContentPane().add(simPanel);
			simPanel.setBackground(Color.WHITE);
			simPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			simPanel.setSize(450, 300);
			
			JList list = new JList();
			frame.getContentPane().add(list);
	}

}
