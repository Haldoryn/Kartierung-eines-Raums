package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Robot;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.SimulatedRoom;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Data.Simulation;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.Serialization.SimulatedRoomDeserializer;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation.UI.SimulationUI;

public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XMLParseException {
	
		
		File input = new File("C:/Users/Julian Vogel/Desktop/Kartierung/Kartierung-eines-Raums/RobotSimulation/SimulatedRoomTemplates/SquareRoom.xml");
		SimulatedRoom room = SimulatedRoomDeserializer.deserialize(input);
		Simulation simulation = new Simulation(room,new Robot(0, new Point2D.Double(500,500),100));
		

		

		
		if(args.length >0 )
		{
			if(Arrays.asList(args).contains("/console"))
			{
				//Start in console mode.
			}
		}
		else
		{		
			//Launch in ui mode.
			SimulationUI window = new SimulationUI(simulation);
		
		}
	}

}
