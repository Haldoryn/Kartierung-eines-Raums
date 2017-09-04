package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Constants;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Constants 
{
	private Document doc;
	private Map<String, Object> myConstants = new HashMap<String, Object>();
	
	 
	/**
	 * @param path
	 * creates a new Constants Class with the path of the XML File as Argument
	 */
	public Constants(String path)
	{
		List<Element> constantsList;
		
		// try catch um das Abbilden der XML Datei auf ein Document Objekt
		try{
			File pfad = new File(path);
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

	public Object getConstbyName(String name)
	{
	
		if( myConstants.get(name)  != null)
			return myConstants.get(name);
		else
			return "No such Element";
		
	}
}
