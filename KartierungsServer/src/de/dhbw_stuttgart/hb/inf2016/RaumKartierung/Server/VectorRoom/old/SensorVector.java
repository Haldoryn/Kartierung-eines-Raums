package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old;

/**
 * Created by samue on 30.05.2017.
 */
public class SensorVector extends Vector{
    private double Spacing;
    private double Angle;
    private RobotVector Robot;

    public SensorVector(RobotVector Robot) {
        this.Robot = Robot;
        refresh();
    }

    public void refresh(){
        double Adjacent = Math.cos(Robot.getAngle()) * Spacing;
        double Opposite = Math.sin(Robot.getAngle()) * Spacing;
        this.setX(Robot.getX() + Adjacent);
        this.setY(Robot.getY() + Opposite);
    }

    public double getAngle() {
        return Angle;
    }

    public void setAngle(double Angle) {
        this.Angle = Angle;
    }

    @Override
    public SensorVector clone(){
        return new SensorVector(Robot);
    }
}
