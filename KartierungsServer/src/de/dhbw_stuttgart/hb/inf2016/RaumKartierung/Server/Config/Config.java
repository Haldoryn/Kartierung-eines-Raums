package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activity.InvalidActivityException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * The Config class provides the possibility to get or set
 *         the Attribute Content of an constant Element of an Attribute in the
 *         Config File Possible return types: Integer, Double, Long, Float,
 *         Short, Boolean, Byte, Char, String
 * @author Jan Wäckers 
 * 
 */
public class Config {
	private Document doc;
	private Map<String, Object> myConstants = new HashMap<String, Object>();

	public Config() throws InvalidActivityException, ParseException {

		// try catch um das Abbilden der XML Datei auf ein Document Objekt
		try {
			File pfad = new File(System.getProperty("user.dir") + "\\Config.xml"); // set path on Current working
																					// directory
			System.out.println("Loading config from path: " + pfad.getAbsolutePath());
			doc = new SAXBuilder().build(pfad);
		} catch (JDOMException e) {
			System.err.println("Error (JDOMException");
			System.err.println("Maybe the XMl Document is incorrect");
		} catch (IOException e) {
			System.err.println("Wrong path or invalid XML Document (IO Exception)");
		}

		readFile();
	}

	/**
	 * @throws InvalidActivityException 
	 * @throws ParseException 
	 * 
	 */
	private void readFile() throws InvalidActivityException, ParseException {

		List<Element> constantsList;
		constantsList = doc.getRootElement().getChildren();

		// gets the name and content of all "constant"-Elements
		// and converts the Content to the right type(specifed by "type")
		for (Iterator<Element> iter = constantsList.iterator(); iter.hasNext();) {
			Element currently = iter.next();
			String name = currently.getAttributeValue("name");
			String content = currently.getText();
			String type = currently.getAttributeValue("type");
			
			NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
			
			Object myValue = null;

			switch (type) {
			case "String":
				myValue = content;
				break;
			case "Integer":
				myValue = Integer.parseInt(content);
				break;
			case "Double":
				myValue = format.parse(content).doubleValue();
				break;
			case "Boolean":
				myValue = Boolean.parseBoolean(content);
				break;
			case "Long":
				myValue = Long.parseLong(content);
				break;
			case "Float":
				myValue = format.parse(content).floatValue();
				break;
			case "Short":
				myValue = Short.parseShort(content);
				break;
			case "Byte":
				myValue = Byte.parseByte(content);
				break;
			case "Char":
				myValue = content.charAt(0);
				break;
			default:
				throw new InvalidActivityException("Invalid config value type: "+name);
			}
			
			if(myValue==null)
			{
				throw new InvalidActivityException("Config value seems to not be parsed correctly: "+name);
			}
			
			myConstants.put(name, myValue);
		}
	}

	/**
	 * returns the Content of an Attribute in the Config File The return type
	 * depends on the type entry in of the Attribute
	 * 
	 */
	public Object getConstbyName(String name) {

		if (myConstants.get(name) != null)
			return myConstants.get(name);
		else
			return "No such Element";

	}
}
