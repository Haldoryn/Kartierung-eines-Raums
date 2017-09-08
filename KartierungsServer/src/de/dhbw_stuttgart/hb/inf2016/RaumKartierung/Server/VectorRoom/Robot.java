package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;
/**
 * 
 * @author Samuel Volz
 * 
 * This class saves the position and the angle of the robot. 
 *
 */
public class Robot {

    private double[] Vector;
    private double Angle;

    /**
     * Returns the position vector of the robot in a double array.
     * @return vector is the current position of the robot.
     */
    public double[] getVector() {
        return Vector;
    }

    /**
     * Saves the position of the robot in a double array.
     * @param vector is the current position of the robot.
     */
    public void setVector(double[] vector) {
        Vector = vector;
    }
    
    /**
     * Returns the angle of the robot.
     * @return angle is the current angle of the robot saved in a double.
     */
    public double getAngle() {
        return Angle;
    }

    /**
     * Saves the angle of the robot in a double.
     * @param angle id the current angle of the robot.
     */
    public void setAngle(double angle) {
        Angle = angle;
    }

    /**
     * The Constructor of the robot needs a position vector and a angle for the initialization. 
     * @param Vector is the current position of the robot.
     * @param Angle is the current angle of the robot.
     */
    public Robot(double[] Vector, double Angle){
        this.Vector = Vector;
        this.Angle = Angle;
    }
}
