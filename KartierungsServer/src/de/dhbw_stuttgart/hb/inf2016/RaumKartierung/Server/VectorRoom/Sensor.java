package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

public class Sensor {
    private Robot robot;
    private double Spacing;
    private double[] Vector = new double[2];
    private double Angle;

    public Sensor(Robot robot) {
        this.robot = robot;
        Angle = 0;
    }

    public void refresh(){
        double Adjacent = Math.cos(robot.getAngle()) * Spacing;
        double Opposite = Math.sin(robot.getAngle()) * Spacing;
        Vector[0] = robot.getVector()[0] + Adjacent;
        Vector[1] = robot.getVector()[1] + Opposite;
    }
    public double[] getVector() {
        return Vector;
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double angle) {
        Angle = angle;
    }
}
