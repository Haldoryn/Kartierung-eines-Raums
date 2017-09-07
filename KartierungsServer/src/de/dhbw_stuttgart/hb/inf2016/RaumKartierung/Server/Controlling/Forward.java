package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Constants.Constants;

/**
 * Created by samue on 20.06.2017.
 */
public class Forward implements Move {
    double LeftMotor;
    double RightMotor;
    Constants cons;

    public Forward(double distence) {
        Distence = distence;
        //Code to Calc LeftMotor and Right Motor
        cons = new Constants("tempPath");
        LeftMotor = Distence/(2 * Math.PI * (double)cons.getConstbyName("wheelRadius")) * 360;
        RightMotor = Distence/(2 * Math.PI * (double)cons.getConstbyName("wheelRadius")) * 360;

    }

    public double getDistence() {
        return Distence;
    }

    public void setDistence(double distence) {
        Distence = distence;
        LeftMotor = Distence/(2 * Math.PI * (double)cons.getConstbyName("wheelRadius")) * 360;
        RightMotor = Distence/(2 * Math.PI * (double)cons.getConstbyName("wheelRadius")) * 360;
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

	@Override
	public void setMotor(double leftMotor, double rightMotor) {
		setDistence((leftMotor + rightMotor) / 2 * (2 * Math.PI * (double)cons.getConstbyName("wheelRadius")) / 360);
	}
}
