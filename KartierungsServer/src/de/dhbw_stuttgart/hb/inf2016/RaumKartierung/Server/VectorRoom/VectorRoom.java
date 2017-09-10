package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Turn;

import java.util.ArrayList;
/**
 * This class calculates the position of a scan point and saves all scan points. 
 * @author Samuel Volz
 *
 */
public class VectorRoom {
    private VectorOperations vectorOperations = new VectorOperations();
    private Robot robot = new Robot(new double[]{0,0}, 0);
    private Sensor sensor = new Sensor(robot);
    private ArrayList<double[]> Points = new ArrayList<>();

    /**
     * Saves a move of the robot.
     * @param move is an object that implements the Interface Move.
     */
    public void movingRobot(Move move){
        if(move instanceof  Turn)
            turningRobot((Turn)move);
        else if(move instanceof Forward)
            drivingRobot((Forward)move);
    }
    
    /**
     * Saves a forward move of the robot.
     * @param forward is an object of the type Forward. 
     */
    public void drivingRobot(Forward forward){
        double Adjacent = Math.cos(Math.toRadians(robot.getAngle())) * forward.getDistence();
        double Opposite = Math.sin(Math.toRadians(robot.getAngle())) * forward.getDistence();
        robot.setVector(vectorOperations.add(robot.getVector(), new double[]{Adjacent, Opposite}));
        sensor.refresh();
    }
    
    /**
     * Saves a turning move of the robot.
     * @param turn is an object of the type Turn. 
     */
    public void turningRobot(Turn turn){
        robot.setAngle(turn.getAngle());
        sensor.refresh();
        sensor.setAngle(sensor.getAngle()+robot.getAngle());
    }

    /**
     * Saves a turning move of the sensor.
     * @param Angle is the angle the sensor turned saved in a double.
     */
    public void turningSensor(double Angle){
        sensor.setAngle(sensor.getAngle() + Angle);
    }

    /**
     * Calculates the position of a scan and saves the scan point.
     * @param distance is the distance the robot scanned something saved in a double.
     */
    public void setScan(double distance){
    	
    	//ToDo get this from somewhere else
    	if(distance > 100 || distance< 0)
    		return;
    	
        double Adjacent = Math.cos(Math.toRadians(sensor.getAngle())) * distance;
        double Opposite = Math.sin(Math.toRadians(sensor.getAngle())) * distance;
        Points.add(new double[]{Adjacent, Opposite});
    }
    
    /**
     * returns a list of the position of all scanned points.
     * @return ArrayList of the position of all scanned points saved in a double array.
     */
    public ArrayList<double[]> getPoints() {
        return Points;
    }
    
    /**
     * returns a list of all scanned points. All Values are positive.
     * @return Arraylist of all scanned points.
     */
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
