package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import java.util.ArrayList;

public class VectorRoom {
    private ArrayList<Vector> Points = new ArrayList<>();
    private ArrayList<EmptyLine> emptyLines = new ArrayList<>();
    private RobotVector Robot = new RobotVector(0,0,0);
    private SensorVector Sensor = new SensorVector(Robot);
    
    public void movingRobot(double distance){
        double Adjacent = Math.cos(Robot.getAngle()) * distance;
        double Opposite = Math.sin(Robot.getAngle()) * distance;
        RobotVector RoboCloneA = Robot.clone();
        Robot.add(new Vector(Adjacent, Opposite));
        emptyLines.add(new EmptyLine( RoboCloneA, Robot.clone()));
        Sensor.refresh();
    }

    public void turningRobot(double Angle){
        Robot.setAngle(Angle);
        Sensor.refresh();
        Sensor.setAngle(Sensor.getAngle()+Robot.getAngle());
    }

    public void turningSensor(double Angle){
        Sensor.setAngle(Angle);
    }
    public void setScan(double distance){
        double Adjacent = Math.cos(Sensor.getAngle()) * distance;
        double Opposite = Math.sin(Sensor.getAngle()) * distance;
        Vector ScanPoint = new Vector(Adjacent,Opposite);
        Points.add(ScanPoint);
        emptyLines.add(new EmptyLine(Robot.clone(), ScanPoint));
    }
    public ArrayList<Vector> getPoints() {
        return Points;
    }
    public ArrayList<EmptyLine> getEmptyLines(){
        return emptyLines;
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
