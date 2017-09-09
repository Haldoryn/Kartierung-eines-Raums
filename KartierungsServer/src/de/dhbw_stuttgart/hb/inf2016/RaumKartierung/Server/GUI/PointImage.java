 package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;

/**
 * the class for the points
 * @author dh10hcn
 *
 */
class Points extends Component 
{

    int xCoordinate;
    int yCoordinate;
    private ArrayList<int[]> coordinates = new ArrayList<>();

    /**
     * saves the new point in a ArrayList
     * @param x the xCoordinate
     * @param y the y Coordinate
     */
    void set(int x, int y) 
    {
    	int[] xAndy = {x, y};
        coordinates.add(xAndy);     
    }

    /**
     * the function which draws all points
     */
    public void paint(Graphics g) 
    {	
    	if(coordinates.size()==0)
    		return;
    	
    	
    	Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0, getHeight() - 1);
        g2d.scale(1, -1);
        
    	for (int[] is : coordinates) 
    	{
			g.drawLine(is[0], is[1], is[0], is[1]);
		}   	
    	
    	try {
			SaveToDisc("D:\\Desktop\\robot.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    
    
    
    /**Saves the points image to a file on the hard disc.
     * @param path The path of the image file
     * @throws IOException Thrown if something goes wrong while writing the file to the hard disc.
     */
    public void SaveToDisc(String path) throws IOException
    {
    	if(path == null)
    	{
    		throw new IllegalArgumentException("Path must not be null");
    	}
    	
    	
    	//Get the dimensions of the image from the points list.
    	int width =0;
    	int heigth=0;
    	for(int i =0;i<coordinates.size();i++)
    	{
    		if(coordinates.get(i)[0]> width)
    		{
    			width= coordinates.get(i)[0];
    		}
    		if(coordinates.get(i)[1]> heigth)
    		{
    			heigth= coordinates.get(i)[1];
    		}
    	}
    
    	
    	//Create a new bufferd image to render the points(buffered image is a in memory image)
    	BufferedImage bi = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
    	Graphics2D g2d = bi.createGraphics();
    	
    	g2d.setBackground(Color.WHITE);
    	g2d.clearRect(0, 0, width, heigth);
        
        //Set the color and draw all points
        g2d.setColor(Color.RED);
    	for (int[] is : coordinates) 
    	{
			g2d.fillRect(is[0], is[1],1,1);
		} 
    	g2d.dispose();
    	
    	//Write the image to the file
    	File outputfile = new File(path);
    	ImageIO.write(bi, "jpg", outputfile);
    }
}

/**
 * the class which is used to create a panel with points
 * @author dh10hcn
 *
 */
public class PointImage extends JApplet 
{
    private int x;
    private int y;
    final Points points = new Points();
    
    /**
     * Constructor for the point panel
     */
    public PointImage()
    {
    	this.add("Center", points);
    }
     
    public void buildUI() {
        
    }
    
    /**
     * the function which has to be called to draw a point
     * @param point the new point to be drawn
     */
    void newPoint(int[] point)
    {  
		x = point[0];
		y = point[1];
		points.set(x, y);
		points.repaint();
    }
    
}
