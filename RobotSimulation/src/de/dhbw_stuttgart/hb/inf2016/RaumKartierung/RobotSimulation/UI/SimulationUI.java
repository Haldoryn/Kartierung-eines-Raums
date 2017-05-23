package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulationData;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.IO.StringEventOutputStream;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.IO.StringOutputListener;

import java.awt.TextArea;

import net.miginfocom.swing.MigLayout;

public class SimulationUI {

	private JFrame frame;
	private TextArea outputList;
	private SimulationPanel simPanel;
	private List<String> lineList = new  LinkedList<String> ();
	private int maxLines = 30;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public SimulationUI( ) throws IOException {
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

		simPanel = new SimulationPanel();
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

	private synchronized void updateOuputText(String text) {
		
		while(lineList.size() >=maxLines)
		{
			lineList.remove(0);
		}
		lineList.add(text);
		StringBuilder sb = new StringBuilder();
		
		for(String line: lineList)
		{
			sb.append(line);
		}
		
		outputList.setText(sb.toString());
	}

	public void setSimulationData(SimulationData data)
	{
		if(data==null)
		{
			throw new IllegalArgumentException("Argument 'data' must not be null.");
		}
		
		simPanel.setSimulationData(data);
	}

}
