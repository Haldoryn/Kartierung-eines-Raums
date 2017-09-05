package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Turn;

import java.util.ArrayList;

public class VectorRoom {
    private ArrayList<Vector> Points = new ArrayList<>();
    private ArrayList<Line> EmptyLines = new ArrayList<>();
    private RobotVector Robot = new RobotVector(0,0,0);
    private SensorVector Sensor = new SensorVector(Robot);
    
    public void movingRobot(double distance){
        double Adjacent = Math.cos(Math.toRadians(Robot.getAngle())) * distance;
        double Opposite = Math.sin(Math.toRadians(Robot.getAngle())) * distance;
        RobotVector RoboCloneA = Robot.clone();
        Robot.add(new Vector(Adjacent, Opposite));
        EmptyLines.add(new Line( RoboCloneA, Robot.clone()));
        Sensor.refresh();
    }
    public void movingRobot(Forward forward){
        movingRobot(forward.getDistence());
    }

    public void turningRobot(double Angle){
        Robot.setAngle(Angle);
        Sensor.refresh();
        Sensor.setAngle(Sensor.getAngle()+Robot.getAngle());
    }
    public void turningRobot(Turn turn){
        turningRobot(turn.getAngle());
    }

    public void turningSensor(double Angle){
        Sensor.setAngle(Angle);
    }
    public void setScan(double distance){
        double Adjacent = Math.cos(Math.toRadians(Sensor.getAngle())) * distance;
        double Opposite = Math.sin(Math.toRadians(Sensor.getAngle())) * distance;
        Vector ScanPoint = new Vector(Adjacent,Opposite);
        Points.add(ScanPoint);
        EmptyLines.add(new Line(Robot.clone(), ScanPoint));
    }
    public ArrayList<Vector> getPoints() {
        return Points;
    }
    public ArrayList<Line> getEmptyLines(){
        return EmptyLines;
    }
    public ArrayList<Vector> getPointsPositivOnly(){
        double lowestX = 0;
        double lowestY = 0;
        for(Vector vector : Points){
            if(vector.getX() < lowestX){
                lowestX = vector.getX();
            }
            if(vector.getY() < lowestY){
                lowestY = vector.getY();
            }
        }
        ArrayList<Vector> PointsPositivOnly = new ArrayList<>();
        for(Vector vector: Points){
            PointsPositivOnly.add(new Vector(vector.getX() + (lowestX * -1), vector.getY() + (lowestY *-1)));
        }
        return PointsPositivOnly;
    }
}
