package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

public class GridMap {

	// Size of each grid cell
	private float cellSize;
	// Min and max coordinate values of the grids
	private int minX, minY, maxX, maxY;
	// Buffer that stores the scan results. Can be converted to a bitmap
	private byte[] buffer = new byte[0];
	//Delta value for float comparisons
	private float comparisonDelta = 0.0001f;

	/**Initializes a new instance of the GridMap class.
	 * @param cellSize The size of each grid cell.
	 */
	public GridMap(float cellSize) {
		if (cellSize <= 0) {
			throw new IllegalArgumentException("cellSize not be 0 or less.");
		}

		this.minX = 0;
		this.minY = 0;
		this.maxX = 0;
		this.maxY = 0;
		this.cellSize = cellSize;
	}

	/**Returns the width of the grid in cells.
	 * @return width of the grid in cells.
	 */
	public int getWidthCells() {
		return Math.abs(minX - maxX);
	}

	/**Returns the height of the grid in cells.
	 * @return height of the grid in cells.
	 */
	public int getHeightCells() {
		return Math.abs(minY - maxY);
	}

	
	/**Gets the dimensions of the grid in cm.
	 * @return dimensions of the grid in cm.
	 */
	public Rectangle getSizeCm() {
		return new Rectangle(minX * cellSize, minY * cellSize, getWidthCells() * cellSize, getHeightCells() * cellSize);
	}

	public void addMeasure(Vector point) {
		resizeToFitPoint(point);

		// Increase value of the cell by 10. A higher value means that the detected
		// point is more reliable because it was detected from multiple angles
		if (buffer[getIndexOfPoint(point)] <= Byte.MAX_VALUE - 10) {
			buffer[getIndexOfPoint(point)] += 10;
		}
	}

	/**Gets the index of a position in the buffer.
	 * @param point The point for which the buffer index should be returned.
	 * @return
	 */
	public int getIndexOfPoint(Vector point) {

		int column = (int) Math.round(point.getX() / cellSize);
		int row = (int) Math.round(point.getY() / cellSize);

		return (this.getWidthCells() * column) + row;
	}
	
	/**Returns the internal buffer. Can be used to display the data in a bitmap.
	 * @return The internal buffer of the current GridMap  instance.
	 */
	public byte[] getBuffer()
	{
		return buffer;
	}

	//Resizes the internal buffer
	private void resizeToFitPoint(Vector point) {
		float x = (float) point.getX();
		float y = (float) point.getY();

		Vector oldStart = new Vector(minX, minY);

		boolean changed = true;

		// Calculate the additional cells on the x axis
		if (x < minX * cellSize && Math.abs(x - (minX * cellSize)) > comparisonDelta) {
			minX += calcAdded(x, minX, cellSize);
			changed = true;
		} else if (x > maxX * cellSize && Math.abs(x - (maxX * cellSize)) > comparisonDelta) {
			maxX += calcAdded(x, maxX, cellSize);
			changed = true;
		}

		// Calculate the additional cells on the y axis
		if (y < minY * cellSize && Math.abs(y - (minY * cellSize)) > comparisonDelta) {
			minY += calcAdded(x, minY, cellSize);
			changed = true;
		} else if (y > maxY * cellSize && Math.abs(y - (minX * cellSize)) > comparisonDelta) {
			maxY += calcAdded(x, maxY, cellSize);
			changed = true;
		}

		// If the grid was resized, realocate a new buffer and copy the old data.
		if (changed) {
			byte[] newBuffer = new byte[getWidthCells() * getHeightCells()];
			System.arraycopy(buffer, 0, newBuffer, getIndexOfPoint(oldStart), buffer.length);
			buffer = newBuffer;
		}

	}

	private static int calcAdded(float newVal, float currentVal, float cellSize) {
		return (int) Math.round((newVal - currentVal * cellSize) / cellSize);
	}

}