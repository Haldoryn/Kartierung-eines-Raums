package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;
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
    private double[] Vector = new double[2];
    private double Angle;
    
    /**
     * the constructor of this class needs a robot for the initialization. 
     * @param robot is an object of the Robot class. It uses it to determine the position of the sensor.
     */
    public Sensor(Robot robot) {
        this.robot = robot;
        Angle = 0;
    }

    /**
     * the refresh method calculates the current position of the sensor using the given robot. 
     */
    public void refresh(){
        double Adjacent = Math.cos(robot.getAngle()) * Spacing;
        double Opposite = Math.sin(robot.getAngle()) * Spacing;
        Vector[0] = robot.getVector()[0] + Adjacent;
        Vector[1] = robot.getVector()[1] + Opposite;
    }
    
    /**
     * Returns the current position of the sensor.
     * @return vector is the current position of the sensor saved in a double array.
     */
    public double[] getVector() {
        return Vector;
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
        Angle = angle;
    }
}
