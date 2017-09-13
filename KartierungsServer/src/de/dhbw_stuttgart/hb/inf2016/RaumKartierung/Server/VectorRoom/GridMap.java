package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import java.util.List;

public class GridMap {

	// Size of each grid cell
	private float cellSize;

	private Vector min;
	private Vector max;

	// Buffer that stores the scan results. Can be converted to a bitmap
	private byte[] buffer = new byte[0];
	// Delta value for float comparisons

	/**
	 * Initializes a new instance of the GridMap class.
	 * 
	 * @param cellSize
	 *            The size of each grid cell.
	 */
	public GridMap(float cellSize, Vector min, Vector max) {
		if (cellSize <= 0) {
			throw new IllegalArgumentException("cellSize not be 0 or less.");
		}

		this.min = min;
		this.max = max;
		this.cellSize = cellSize;
		buffer = new byte[getWidthCells()*getHeightCells()];
	}

	/**
	 * Returns the width of the grid in cells.
	 * 
	 * @return width of the grid in cells.
	 */
	public int getWidthCells() {
		return (int) Math.round(Math.abs(min.getX() - max.getX()) / cellSize);
	}

	/**
	 * Returns the height of the grid in cells.
	 * 
	 * @return height of the grid in cells.
	 */
	public int getHeightCells() {
		return (int) Math.round(Math.abs(min.getY() - max.getY()) / cellSize);
	}

	/**
	 * Gets the dimensions of the grid in cm.
	 * 
	 * @return dimensions of the grid in cm.
	 */
	public Rectangle getSizeCm() {
		return new Rectangle((float) (min.getX() / cellSize), (float) min.getY() / cellSize, getWidthCells() * cellSize,
				getHeightCells() * cellSize);
	}

	public void addMeasure(Vector point) {	
		int index = getIndexOfPoint(point);	
		buffer[index] =Byte.MAX_VALUE;
	}
	
	public void addMeasureRange(List<Vector> points)
	{
		for(Vector point: points)
			addMeasure(point);
	}

	/**
	 * Gets the index of a position in the buffer.
	 * 
	 * @param point
	 *            The point for which the buffer index should be returned.
	 * @return
	 */
	public int getIndexOfPoint(Vector point) {	
		int row = (int) Math.round(point.getY() / cellSize);
		if(row > 0)
		{
			row--;
		}
		
		int column = (int) Math.round(point.getX() / cellSize);

		return (this.getWidthCells() * row) + column;
	}

	/**
	 * Returns the internal buffer. Can be used to display the data in a bitmap.
	 * 
	 * @return The internal buffer of the current GridMap instance.
	 */
	public byte[] getBuffer() {
		return buffer;
	}

}