package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Class for displaying all scanned points.
 * 
 * @author dh10hcn
 *
 */
public class MapDiplay extends Component {

	int xCoordinate;
	int yCoordinate;
	private ArrayList<Point> coordinates = new ArrayList<>();

	/**
	 * saves the new point in a ArrayList
	 * 
	 * @param x
	 *            the xCoordinate
	 * @param y
	 *            the y Coordinate
	 */
	public void addPoint(Point point) {
		if(point == null)
		{
			throw new IllegalArgumentException("The 'point' parameter must not be null");
		}
		
		coordinates.add(point);
		this.repaint();
	}

	public void clearPoints() {
		coordinates.clear();
		this.repaint();
	}

	/**
	 * the function which draws all points
	 */
	public void paint(Graphics g) {
		if (coordinates.size() == 0)
			return;

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(0, getHeight() - 1);
		g2d.scale(1, -1);

		BufferedImage image = renderToImage();
		g2d.drawImage(image, 0, 0,this.getWidth(),this.getHeight(), null);
	}

	/**
	 * Renders the coordinates to a BufferedImage.
	 * 
	 * @return The rendered image.
	 */
	public BufferedImage renderToImage() {
		// Get the dimensions of the image from the points list.
		int width = 0;
		int heigth = 0;
		for (int i = 0; i < coordinates.size(); i++) {
			if (coordinates.get(i).x > width) {
				width= coordinates.get(i).x;
			}
			if (coordinates.get(i).y > heigth) {
				heigth = coordinates.get(i).y;
			}
		}

		// Create a new bufferd image to render the points(buffered image is a in memory
		// image)
		BufferedImage bi = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();

		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, heigth);

		// Set the color and draw all points
		g2d.setColor(Color.RED);
		for (Point point : coordinates) {
			g2d.fillRect(point.x, point.y, 1, 1);
		}
		g2d.dispose();

		return bi;
	}

	/**
	 * Saves the points image to a file on the hard disc.
	 * 
	 * @param path
	 *            The path of the image file
	 * @throws IOException
	 *             Thrown if something goes wrong while writing the file to the hard
	 *             disc.
	 */
	public void saveToDisc(String path, BufferedImage image) throws IOException {
		if (path == null) {
			throw new IllegalArgumentException("Path must not be null");
		}

		// Write the image to the file
		File outputfile = new File(path);
		ImageIO.write(image, "jpg", outputfile);
	}
}