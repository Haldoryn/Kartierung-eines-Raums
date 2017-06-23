package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

/**
 * Created by samue on 20.06.2017.
 */
public class Turn implements Move {
    double LeftMotor;
    double RightMotor;

    public Turn(double angle) {
        Angle = angle;
        //Code to Calc Left and Right Motor
        double n = (50/*Temp Abstant zwischen den reifen*/ * angle)/(2 * 50/* Temp Radius der Reifen*/ * 360);
        if(angle > 0){
            LeftMotor = 360 * n;
            RightMotor = - 360 * n;
        } else if(angle < 0){
            RightMotor = 360 * n;
            LeftMotor = - 360 * n;
        } else {
            LeftMotor = 0;
            RightMotor = 0;
        }
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        Angle = angle;
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
