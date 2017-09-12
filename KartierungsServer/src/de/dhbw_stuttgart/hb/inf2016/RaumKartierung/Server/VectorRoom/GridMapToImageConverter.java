package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class GridMapToImageConverter {

	public static Image Convert(GridMap map) {
		
		//Create a image with the dimensions of the map.
		BufferedImage image = new BufferedImage(map.getWidthCells(), map.getHeightCells(),
				BufferedImage.TYPE_BYTE_GRAY);

		// Get the backing pixels, and copy into it
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy( map.getBuffer(), 0, data, 0,  map.getBuffer().length);

		return image;
	}
}
