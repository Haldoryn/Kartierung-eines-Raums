package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

/**
 * Created by samue on 20.06.2017.
 */
public class Forward implements Move {
    double LeftMotor;
    double RightMotor;

    public Forward(double distence) {
        Distence = distence;
        //Code to Calc LeftMotor and Right Motor
    }

    public double getDistence() {
        return Distence;
    }

    public void setDistence(double distence) {
        Distence = distence;
    }

    double Distence;
    @Override
    public double getLeftMotor() {
        return LeftMotor;
    }

    @Override
    public double getRightMotor() {
        return RightMotor;
    }
}
