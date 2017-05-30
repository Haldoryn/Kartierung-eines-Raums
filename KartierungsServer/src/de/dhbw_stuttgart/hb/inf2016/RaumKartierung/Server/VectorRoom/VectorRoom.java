package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import java.util.ArrayList;

public class VectorRoom {
    ArrayList<Vector> Points = new ArrayList<>();
    RobotVector Robot = new RobotVector(0,0,0);
    SensorVector Sensor = new SensorVector(Robot);
    
    public void movingRobot(double distance){
        double Adjacent = Math.cos(Robot.Angle) * distance;
        double Opposite = Math.sin(Robot.Angle) * distance;
        Robot.setVector(Robot.getVector().add(new Vector(Adjacent, Opposite)));
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
        double Adjacent = Math.cos(Sensor.Angle) * distance;
        double Opposite = Math.sin(Sensor.Angle) * distance;
        Points.add(new Vector(Adjacent,Opposite));
    }
}
