package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

/**
 * Is an interface for all moves the robot performs. 
 * @author Samuel Volz
 *
 */
public interface Move {
	/**
	 * @return the angle the left motor has to turn in order to achieve the move.
	 */
    public double getLeftMotor();
	/**
	 * @return the angle the right motor has to turn in order to achieve the move.
	 */
    public double getRightMotor();
}
