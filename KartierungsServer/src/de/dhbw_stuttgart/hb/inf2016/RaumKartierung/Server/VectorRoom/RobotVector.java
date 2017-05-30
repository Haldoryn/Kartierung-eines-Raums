package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * Created by samue on 30.05.2017.
 */
public class RobotVector{

    private Vector Vector;
    private double Angle;

    public RobotVector(int x, int y, double Angle) {
        this.Angle = Angle;
        Vector = new Vector(x,y);
    }
    public RobotVector(Vector Vector, double Angle){
        this.Angle = Angle;
        this.Vector = Vector;
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        this.Angle = angle;
    }

    public Vector getVector() {
        return Vector;
    }

    public void setVector(Vector vector) {
        Vector = vector;
    }
}
