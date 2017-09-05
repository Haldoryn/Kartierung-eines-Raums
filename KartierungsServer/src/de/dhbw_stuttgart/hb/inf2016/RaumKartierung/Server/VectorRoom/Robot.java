package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

public class Robot {

    private double[] Vector;
    private double Angle;

    public double[] getVector() {
        return Vector;
    }

    public void setVector(double[] vector) {
        Vector = vector;
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        Angle = angle;
    }

    public Robot(double[] Vector, double Angle){
        this.Vector = Vector;
        this.Angle = Angle;
    }
}
