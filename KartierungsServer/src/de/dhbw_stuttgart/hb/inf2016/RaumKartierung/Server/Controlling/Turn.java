package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;

/**
 * The turn class is a turning move. It stores the angle in which the robot should turn
 *  and the angle in which the motors have to turn in order to achieve this angle. 
 * @author Samuel Volz
 *
 */
public class Turn implements Move {
    double LeftMotor;
    double RightMotor;
    Config cons;
    /**
     * Initializes the turn class.
     * @param angle is the angle in which the robot should turn.
     * @param cfg
     */
    public Turn(double angle,Config cfg) {
        cons = cfg;
        setAngle(angle);
    }
    /**
     * @return the angle in which the robot should turn.
     */
    public double getAngle() {
        return Angle;
    }
    /**
     * This method calculates the angles the left and the right motor has to turn in order to achieve the turn move.
     * @param angle is the angle in which the robot should turn.
     */
    public void setAngle(double angle) {
        Angle = angle%360;
        double n = ((double)cons.getConstbyName("wheelDistance") * angle)/(2 * (double)cons.getConstbyName("wheelRadius") * 360);
        if(angle < 0){
        	//If the angle is positive, the robot should turn left in order to stay true to the unit cycle.
            LeftMotor = 360 * n;
            RightMotor = - 360 * n;
        } else if(angle > 0){
        	//If the angle is negative, the robot should turn right.
            RightMotor = 360 * n;
            LeftMotor = - 360 * n;
        } else {
            LeftMotor = 0;
            RightMotor = 0;
        }
    }

    double Angle;
    @Override
    public double getLeftMotor() {
        return LeftMotor;
    }

    @Override
    public double getRightMotor() {
        return RightMotor;
    }
}
