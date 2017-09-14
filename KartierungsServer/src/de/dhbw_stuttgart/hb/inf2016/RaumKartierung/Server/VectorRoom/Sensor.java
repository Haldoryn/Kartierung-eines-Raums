package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;

/**
 * This class saves the position and the angle of the sensor on the robot.
 * It calculates the current position of the sensor using the spacing between the rotation point of the robot 
 * and the sensor and and the position and angle of the robot. 
 * @author Samuel Volz
 *
 */
public class Sensor {
    private Robot robot;
    private double Spacing;
    private Vector vector = new Vector();
    private double Angle;
    
    /**
     * the constructor of this class needs a robot for the initialization. 
     * @param robot is an object of the Robot class. It uses it to determine the position of the sensor.
     */
    public Sensor(Robot robot, Config config) {
        this.robot = robot;
        Spacing = (double)config.getConstbyName("Spacing");
        Angle = 0;
    }

    /**
     * the refresh method calculates the current position of the sensor using the given robot. 
     */
    public void refresh(){
        double Adjacent = Math.cos(robot.getAngle()) * Spacing;
        double Opposite = Math.sin(robot.getAngle()) * Spacing;
        vector = robot.getVector().add(new Vector(Adjacent, Opposite));
    }
    
    /**
     * Returns the current position of the sensor.
     * @return vector is the current position of the sensor saved in a double array.
     */
    public Vector getVector() {
        return vector;
    }

    /**
     * Returns the current angle of the sensor.
     * @return angle is the current angle of the sensor saved in a double.
     */
    public double getAngle() {
        return Angle;
    }

    /**
     * Saves the angle of the sensor.
     * @param angle is the current angle of the sensor in a double.
     */
    public void setAngle(double angle) {
        Angle = angle%360;
    }

	@Override
	public String toString() {
		return "Sensor [vector=" + vector + ", Angle=" + Math.round(Angle) + "]";
	}
}
