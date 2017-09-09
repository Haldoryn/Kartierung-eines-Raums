package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;


/**
 * @author Jan Wäckers
 * The Config class provides the possibility to get or set the Attribute Content of an constant Element
 * of an Attribute in the Config File 
 * Possible return types: Integer, Double, Long, Float, Short, Boolean, Byte, Char, String
 * 
 */
public class Config 
{
	private Document doc;
	private Map<String, Object> myConstants = new HashMap<String, Object>();
	
	public Config()
	{
		
		// try catch um das Abbilden der XML Datei auf ein Document Objekt
		try{
			 File pfad = new File(System.getProperty("user.dir\\Config.xml")); // set path on Current working directory
			 doc = new SAXBuilder().build(pfad);	 
		} 
		catch(JDOMException e){
			System.err.println("Error (JDOMException");
			System.err.println("Maybe the XMl Document is incorrect");
		}
		catch(IOException e)
		{
			System.err.println("Wrong path or invalid XML Document (IO Exception)");
		}
		
		 readFile();
	}	
	
	/**
	 * 
	 */
	private void readFile(){
		
		List<Element> constantsList;
		constantsList= doc.getRootElement().getChildren();
		 
		// gets the name and content of all "constant"-Elements
		// and converts the Content to the right type(specifed by "type")  
		for(Iterator<Element> iter = constantsList.iterator(); iter.hasNext();)
		{	
			Element currently = iter.next();
			String name = currently.getAttributeValue("name");
			String content = currently.getText();
			String type = currently.getAttributeValue("type");
			Object myValue = (String) "error";
			
			switch(type)
			{
			case "String": myValue = content;
						    break;
			case "Integer": myValue = Integer.parseInt(content);
						    break;
			case "Double": myValue = Double.parseDouble(content);
						    break;
			case "Boolean": myValue = Boolean.parseBoolean(content);
							break;
			case "Long": myValue =Long.parseLong(content);
							break;
			case "Float": myValue =Float.parseFloat(content);
							break;
			case "Short": myValue = Short.parseShort(content);
							break;
			case "Byte": myValue = Byte.parseByte(content);
							break;
			case "Char": myValue = content.charAt(0);
							break;
			}
			myConstants.put(name, myValue);
		}
	}

	/**
	 * returns the Content of an Attribute in the Config File 
	 * The return type depends on the type entry in of the Attribute 
	 * 
	 */
	public Object getConstbyName(String name)
	{
	
		if( myConstants.get(name)  != null)
			return myConstants.get(name);
		else
			return "No such Element";
		
	}
	
	/**
	 * Method for setting a Attributes Content in the Config File
	 * @param elementName
	 *  Name of the Attribute
	 * @param elementContent
	 * the new Attribute Content
	 */
	public void setConst(String elementName, String elementContent){
		
		doc.getRootElement().getChild(elementName).addContent(elementContent);
		readFile(); // update myConstans 
	}
}
