package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.PrintStream;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.StringEventOutputStream;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.StringOutputListener;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;

import java.awt.TextArea;

import net.miginfocom.swing.MigLayout;

public class SimulationUI {

	private Simulation simulation;

	private JFrame frame;
	private TextArea outputList;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public SimulationUI(Simulation sim) throws IOException {
		this.simulation = sim;
		initialize();
		this.frame.setVisible(true);

		System.out.println("Simulation UI created.");
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setTitle("Robot Simulation UI");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[70%:70%,grow][20%:20%,grow]", "[95%:n,top]"));

		SimulationPanel simPanel = new SimulationPanel(simulation);
		simPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.getContentPane().add(simPanel, "cell 0 0,grow");
		simPanel.setBackground(Color.WHITE);
		simPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		simPanel.setSize(450, 300);

		outputList = new TextArea();
		outputList.setEditable(false);
		frame.getContentPane().add(outputList, "cell 1 0,grow");

		StringEventOutputStream stream = new StringEventOutputStream();
		stream.AddListener(new StringOutputListener() {
			@Override
			public void handleString(String data) {
				updateOuputText(data);
			}
		});
		System.setOut(stream);
	}
	
	private synchronized void updateOuputText(String text)
	{
		String data = outputList.getText() ;
		outputList.setText(data + text);
	}

}
