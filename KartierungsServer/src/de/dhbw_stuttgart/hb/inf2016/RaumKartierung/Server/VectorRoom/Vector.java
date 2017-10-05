package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * This class stores the x and y value of a vector.
 * @author Samuel Volz
 *
 */
public class Vector {
	/**
	 * The x value of the vector.
	 */
    private double x;
    /**
     * The y value of the vector.
     */
    private double y;

    /**
     * Initializes the vector.
     * @param x
     * @param y
     */
    public Vector(double x,double y){
        this.x = x;
        this.y = y;
    }

    public Vector() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Adds two vectors together.
     * @param Vec gets added with this vector.
     * @return a new vector.
     */
    public Vector add(Vector Vec){
    	double thisX = this.x;
    	double vecX = Vec.getX();
    	double thisY = this.y;
    	double vecY = Vec.y;
        return new Vector(thisX + vecX, thisY + vecY);
    }

    /**
     * returns a new Vector with the values of this vector.
     */
    public Vector clone(){
        return new Vector(x,y);
    }

	@Override
	public String toString() {
		return "Vector [x=" + Math.round(x) + ", y=" + Math.round(y) + "]";
	}  
}
