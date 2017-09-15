package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;

/**
 * The forward class is a move forward. It stores the distance and the angle in which the motors have to turn in order to achieve this distance. 
 * @author Samuel Volz
 *
 */
public class Forward implements Move {
	/**
	 * The angle the left motor has to turn in order to achieve the given distance.
	 */
    double LeftMotor;
	/**
	 * The angle the right motor has to turn in order to achieve the given distance.
	 */
    double RightMotor;
    Config cfg;
    
    /**
     * Constructor of Forward class.
     * It takes a distance and calculates it into the movement of the motors.
     * @param distance is the distance the robot should move forward. 
     * @param cfg is the object that takes configurations from an xml file.
     */
    public Forward(double distance,Config cfg) {
        Distance = distance;
        //Code to Calc LeftMotor and Right Motor
        this.cfg = cfg;
        LeftMotor = Distance/(2 * Math.PI * (double)cfg.getConstbyName("wheelRadius")) * 360;
        RightMotor = Distance/(2 * Math.PI * (double)cfg.getConstbyName("wheelRadius")) * 360;

    }

    /**
     * Returns the distance the robot should move forward.
     * @return the distance
     */
    public double getDistence() {
        return Distance;
    }

    /**
     * Takes the distance and calculates it into the movement of the motors.
     * @param distence the distance the robot should move forward
     */
    public void setDistence(double distence) {
        Distance = distence;
        LeftMotor = Distance/(2 * Math.PI * (double)cfg.getConstbyName("wheelRadius")) * 360;
        RightMotor = Distance/(2 * Math.PI * (double)cfg.getConstbyName("wheelRadius")) * 360;
    }

    double Distance;
    @Override
    public double getLeftMotor() {
        return LeftMotor;
    }

    @Override
    public double getRightMotor() {
        return RightMotor;
    }
    /**
     * @param leftMotor is the angle the left motor has to turn in order to achieve the move.
     * @param rightMotor is the angle the right motor has to turn in order to achieve the move.
     */
	public void setMotor(double leftMotor, double rightMotor) {
		setDistence((leftMotor + rightMotor) / 2 * (2 * Math.PI * (double)cfg.getConstbyName("wheelRadius")) / 360);
	}
}
