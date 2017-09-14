package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import java.util.ArrayList;
/**
 * This class calculates the position of a scan point and saves all scan points. 
 * @author Samuel Volz
 *
 */
public class VectorRoom {
    private ArrayList<Vector> Points = new ArrayList<>();
    private Sensor sensor;
    public VectorRoom (Sensor sensor) {
    	this.sensor = sensor;
    }
    
    /**
     * Calculates the position of a scan and saves the scan point.
     * @param distance is the distance the robot scanned something saved in a double.
     */
    public void setScan(double distance){
        double Adjacent = Math.cos(Math.toRadians(sensor.getAngle())) * distance;
        double Opposite = Math.sin(Math.toRadians(sensor.getAngle())) * distance;
        Points.add(sensor.getVector().add(new Vector(Adjacent, Opposite)));
    }
    
    /**
     * returns a list of the position of all scanned points.
     * @return ArrayList of the position of all scanned points saved in a double array.
     */
    public ArrayList<Vector> getPoints() {
        return Points;
    }
    
    /**
     * returns a list of all scanned points. All Values are positive.
     * @return Arraylist of all scanned points.
     */
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
            PointsPositivOnly.add(new Vector(vector.getX() + Math.abs(lowestX), vector.getY() + Math.abs(lowestY)));
        }
        return PointsPositivOnly;
    }
}
