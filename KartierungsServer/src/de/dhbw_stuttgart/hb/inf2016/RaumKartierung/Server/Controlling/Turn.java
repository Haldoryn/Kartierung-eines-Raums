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
