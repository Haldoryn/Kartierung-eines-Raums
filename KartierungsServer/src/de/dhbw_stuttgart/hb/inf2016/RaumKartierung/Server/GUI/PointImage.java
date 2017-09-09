 package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.*;
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
    	Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0, getHeight() - 1);
        g2d.scale(1, -1);
        
    	for (int[] is : coordinates) 
    	{
			g.drawLine(is[0], is[1], is[0], is[1]);
		}   	
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
