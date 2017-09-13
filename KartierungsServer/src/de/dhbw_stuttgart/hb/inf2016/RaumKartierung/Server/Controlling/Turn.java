package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;

/**
 * Created by samue on 20.06.2017.
 */
public class Turn implements Move {
    double LeftMotor;
    double RightMotor;
    Config cons;

    public Turn(double angle,Config cfg) {
        cons = cfg;
        setAngle(angle);
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        Angle = angle%360;
        double n = ((double)cons.getConstbyName("wheelDistance") * angle)/(2 * (double)cons.getConstbyName("wheelRadius") * 360);
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

    double Angle;
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
		
	}
}
