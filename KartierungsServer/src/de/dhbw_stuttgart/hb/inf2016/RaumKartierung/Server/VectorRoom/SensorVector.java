package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * Created by samue on 30.05.2017.
 */
public class SensorVector{
    Vector Vector = new Vector();
    double Spacing;
    double Angle;
    RobotVector Robot;

    public SensorVector(RobotVector Robot) {
        this.Robot = Robot;
        refresh();
    }

    public void refresh(){
        double Adjacent = Math.cos(Robot.Angle) * Spacing;
        double Opposite = Math.sin(Robot.Angle) * Spacing;
        Vector = Robot.getVector().add(new Vector(Adjacent,Opposite));
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double Angle) {
        this.Angle = Angle;
    }
}
