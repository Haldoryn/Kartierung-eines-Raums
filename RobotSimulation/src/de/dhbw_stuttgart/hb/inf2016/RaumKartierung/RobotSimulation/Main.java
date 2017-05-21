package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XMLParseException {
		
		File input = new File("E:/Kartierung-eines-Raums/RobotSimulation/SimulatedRoomTemplates/SquareRoom.xml");
		SimulatedRoom room = SimulatedRoomDeserializer.deserialize(input);
		Simulation simulation = new Simulation(room);
		
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
