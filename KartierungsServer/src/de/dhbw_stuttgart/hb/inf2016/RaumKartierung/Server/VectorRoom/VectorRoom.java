package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Turn;

import java.util.ArrayList;

public class VectorRoom {
    private VectorOperations vectorOperations = new VectorOperations();
    private Robot robot = new Robot(new double[]{0,0}, 0);
    private Sensor sensor = new Sensor(robot);
    private ArrayList<double[]> Points = new ArrayList<>();

    public void drivingRobot(double distance){
        double Adjacent = Math.cos(Math.toRadians(robot.getAngle())) * distance;
        double Opposite = Math.sin(Math.toRadians(robot.getAngle())) * distance;
        robot.setVector(vectorOperations.add(robot.getVector(), new double[]{Adjacent, Opposite}));
        sensor.refresh();
    }
    public void drivingRobot(Forward forward){
        drivingRobot(forward.getDistence());
    }
    public void movingRobot(Move move){
        if(move instanceof  Turn)
            turningRobot((Turn)move);
        else if(move instanceof Forward)
            drivingRobot((Forward)move);
    }
    public void turningRobot(double Angle){
        robot.setAngle(Angle);
        sensor.refresh();
        sensor.setAngle(sensor.getAngle()+robot.getAngle());
    }
    public void turningRobot(Turn turn){
        turningRobot(turn.getAngle());
    }

    public void turningSensor(double Angle){
        sensor.setAngle(Angle);
    }

    public void setScan(double distance){
        double Adjacent = Math.cos(Math.toRadians(sensor.getAngle())) * distance;
        double Opposite = Math.sin(Math.toRadians(sensor.getAngle())) * distance;
        Points.add(new double[]{Adjacent, Opposite});
    }
    public ArrayList<double[]> getPoints() {
        return Points;
    }
    public ArrayList<double[]> getPointsPositivOnly(){
        double lowestX = 0;
        double lowestY = 0;
        for(double[] vector : Points){
            if(vector[0] < lowestX){
                lowestX = vector[0];
            }
            if(vector[1] < lowestY){
                lowestY = vector[1];
            }
        }
        ArrayList<double[]> PointsPositivOnly = new ArrayList<>();
        for(double[] vector: Points){
            PointsPositivOnly.add(new double[]{vector[0] + (lowestX * -1), vector[1] + (lowestY *-1)});
        }
        return PointsPositivOnly;
    }
}
