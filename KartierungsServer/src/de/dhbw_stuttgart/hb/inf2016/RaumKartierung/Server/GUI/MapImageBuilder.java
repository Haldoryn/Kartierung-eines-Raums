package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Vector;

/**
 * Provides static methods for rendering the robot map.
 * 
 * @author JVogel
 *
 */
public class MapImageBuilder {

	/**
	 * Creates the image of the map.
	 * 
	 * @param points
	 *            The map points.
	 * @param roboPos
	 *            Current position of the robot.
	 * @return The map image.
	 */
	public static BufferedImage createMapImage(List<Vector> points, Vector roboPos) {
		if (points.size() == 0)
			return null;

		// Get the size that the new image needs to have.
		float maxX = 0;
		float maxY = 0;

		for (Vector point : points) {
			if (point.getX() > maxX)
				maxX = (float) point.getX();
			if (point.getY() > maxY)
				maxY = (float) point.getY();
		}

		// Abort if the size would be x/0 or 0/y.
		if (Math.round(maxX) == 0 || Math.round(maxY) == 0)
			return null;

		// Create the image and clear the background with wite.
		BufferedImage img = new BufferedImage(Math.round(maxX), Math.round(maxY), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, Math.round(maxX), Math.round(maxY));

		// Draw all points. Draw 4 pixel per point to get better visible points.
		g.setColor(Color.RED);
		for (Vector point : points) {
			g.fillRect((int) Math.round(point.getX()) - 1, (int) Math.round(point.getY()) - 1, 2, 2);
		}

		// Draw the robot. Always draw it at 1/100 of the bigger axis.
		g.setColor(Color.BLUE);
		int size = Math.round(Math.max(img.getWidth(), img.getHeight()) / 100);
		g.drawRect((int) Math.round(roboPos.getX()) - size / 2, (int) Math.round(roboPos.getY()) - size / 2, size,
				size);

		return img;
	}
}
