package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * Created by samue on 30.05.2017.
 */
public class RobotVector extends Vector{


    private double Angle;

    public RobotVector(double x, double y, double Angle) {
        this.Angle = Angle;
        this.setX(x);
        this.setY(y);
    }
    public RobotVector(double Angle){
        this.Angle = Angle;
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        this.Angle = angle;
    }
    @Override
    public RobotVector clone(){
        return new RobotVector(getX(),getY(),Angle);
    }
}
