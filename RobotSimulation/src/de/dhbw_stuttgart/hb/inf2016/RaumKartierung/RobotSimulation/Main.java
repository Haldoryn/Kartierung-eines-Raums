package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.management.modelmbean.XMLParseException;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Robot;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulatedRoom;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulationData;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Serialization.SimulatedRoomDeserializer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.SimulationUI;

public class Main {

	 static int angle = 0;
	
	public static void main(String[] args)
			throws SAXException, IOException, ParserConfigurationException, XMLParseException {

		File input = new File(
				"C:/Users/Jan Wäckers/Desktop/SquareRoom.xml");
		SimulatedRoom room = SimulatedRoomDeserializer.deserialize(input);
		SimulationData simulation = new SimulationData(room, new Robot());

		SimulationUI ui = new SimulationUI();
		simulation.getRobot().setPosition(new Point2D.Double(2500,2500));
		ui.setSimulationData((SimulationData)simulation.clone());
		
		
		Random rng = new Random();
		Timer timer = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			//	simulation.getRobot().move(-10,-10);
				
				simulation.getRobot().setRotation(45);
				simulation.getRobot().setSensorRotation((angle+=45)%360);
				ui.setSimulationData((SimulationData)simulation.clone());
		
			}
		});
		timer.start();
	}

}
