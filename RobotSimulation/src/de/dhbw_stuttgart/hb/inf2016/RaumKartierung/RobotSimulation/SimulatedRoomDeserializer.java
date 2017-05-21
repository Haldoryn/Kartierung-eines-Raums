package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**Class for deserializing simulated room XML documents.
 * @author Julian Vogel
 */
public class SimulatedRoomDeserializer {

	private final static String simulatedRoomNodeName = "simulatedRoom";
	private final static String outlineName = "outline";
	private final static String obstaclesName = "obstacles";
	private final static String obstacleName = "obstacle";
	private final static String pointName = "point";
	private final static String nameAttributeName = "name";
	private final static String xAttributeName = "x";
	private final static String yAttributeName = "y";

	/**
	 * Loads and parses a simulated room template file. Returns a
	 * {@link SimulatedRoom}
	 * @param xmlFile The {@link File} the file that should be parsed.
	 * @return The parsed {@link SimulatedRoom} object.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws XMLParseExceptionIs thrown if the file can not be parsed because of invalid or missing data.
	 */
	public static SimulatedRoom deserialize(File xmlFile)
			throws SAXException, IOException, ParserConfigurationException, XMLParseException {

		// Parameter null check.
		if (xmlFile == null) {
			throw new InvalidParameterException("The xml File parameter must not be null");
		}

		// Create a XML document and parse the file into it.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document document = docBuilder.parse(xmlFile);

		// Check if there is a simulatedRoom XML node, otherwise the document is
		// invalid.
		Node simulatedRoomNode = document.getFirstChild();
		if (!simulatedRoomNode.getNodeName().equals(simulatedRoomNodeName)) {
			throw new XMLParseException("The document does not contain a '" + simulatedRoomNodeName + "' node.");
		}

		// Find the outlines and obstacles node.
		NodeList children = simulatedRoomNode.getChildNodes();
		Outline outline = null;
		List<Obstacle> obstacles = null;

		for (int index = 0; index < children.getLength(); index++) {
			Node node = children.item(index);
			if (node.getNodeName().equals(outlineName)) {
				outline = parseOutline(node);
			} else if (node.getNodeName().equals(obstaclesName)) {
				obstacles = parseObstacles(node);
			} else {
				// A node with unexpected name was found(something else than
				// "outline" or "obstacles"
				throw new XMLParseException("Unknown child of main node'" + node.getNodeName() + "'.");
			}
		}

		// Check if both XML nodes were found.
		if (outline == null) {
			throw new XMLParseException(
					"No '" + outlineName + "' node was found. Could not load simultated room data.");
		}
		if (obstacles == null) {
			throw new XMLParseException(
					"No '" + obstaclesName + "' node was found. Could not load simultated room data.");
		}

		return new SimulatedRoom(outline, obstacles, xmlFile.getName());
	}

	/**
	 * Parses a outline XML node and returns a {@link Outline} object.
	 * @param outlineNode The outline node from the input XML document.
	 * @return The parsed {@link Outline}
	 * @throws XMLParseException
	 */
	private static Outline parseOutline(Node outlineNode) throws XMLParseException {
		return new Outline(parsePointNodeList(outlineNode));
	}

	/**Parses a list point XML nodes and returns a list of {@link Point2D} objects.
	 * @param pointsListNode The XML node that has the point nodes as its children.
	 * @return List of {@link Point2D} objects
	 * @throws XMLParseException Is throw if invalid data is detected.
	 */
	private static ArrayList<Point2D> parsePointNodeList(Node pointsListNode) throws XMLParseException {
		ArrayList<Point2D> points = new ArrayList<>();
		NodeList children = pointsListNode.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node node = children.item(index);
			if (node.getNodeName().equals(pointName)) {
				points.add(parsePoint(node));
			} else {
				// A node with unexpected name was found.
				throw new XMLParseException(
						"Unknown child of " + pointsListNode.getNodeName() + " '" + node.getNodeName() + "'.");
			}
		}
		return points;
	}

	/**
	 * Parses a obstacles XML node and returns a List of {@link Obstacle} objects.
	 * @param obstaclesNode The obstacles node from the input XML document.
	 * @return List of parsed {@link Obstacle} objects.
	 * @throws XMLParseException
	 */
	private static List<Obstacle> parseObstacles(Node obstaclesNode) throws XMLParseException {
		List<Obstacle> obstacles = new ArrayList<Obstacle>();

		NodeList children = obstaclesNode.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node node = children.item(index);
			if (node.getNodeName().equals(obstacleName)) {
				String name = node.getAttributes().getNamedItem(nameAttributeName).getNodeValue();
				List<Point2D> points = parsePointNodeList(node);
				obstacles.add(new Obstacle(points, name));

			} else {
				// A node with unexpected name was found.
				throw new XMLParseException(
						"Unknown child of " + obstaclesNode.getNodeName() + " '" + node.getNodeName() + "'.");
			}
		}

		return obstacles;
	}

	/**Parses a point XML node and returns a {@link Point2D} object.
	 * @param pointNode The point node from the input XML document.
	 * @return the parsed {@link Point2D} object.
	 * @throws XMLParseException Is thrown if the node contains invalid or missing data.
	 */
	private static Point2D parsePoint(Node pointNode) throws XMLParseException {
		String xValue = pointNode.getAttributes().getNamedItem(xAttributeName).getNodeValue();
		String yValue = pointNode.getAttributes().getNamedItem(yAttributeName).getNodeValue();

		if (xValue == null) {
			throw new XMLParseException("Missing xml node attribute '" + xAttributeName + "'");
		}
		if (yValue == null) {
			throw new XMLParseException("Missing xml node attribute '" + yAttributeName + "'");
		}

		//Parse the x and y value. 
		//Maybe add better exception handling here.
		double x = Double.parseDouble(xValue);
		double y = Double.parseDouble(yValue);

		return new Point2D.Double(x, y);
	}
}
