package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;


/**
 * Created by samue on 20.06.2017.
 */
public interface Move {
    public double getLeftMotor();
    public double getRightMotor();
    public void setMotor(double leftMotor, double rightMotor);
}
