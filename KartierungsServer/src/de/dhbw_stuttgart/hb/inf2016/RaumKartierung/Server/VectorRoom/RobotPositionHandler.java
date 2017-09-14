package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Turn;

/**
 * 
 * @author Samuel Volz
 *
 */
public class RobotPositionHandler {
    private VectorOperations vectorOperations = new VectorOperations();
    private Robot robot;
    private Sensor sensor;
    
    public RobotPositionHandler(Robot robot, Sensor sensor) {
    	this.robot = robot;
    	this.sensor = sensor;
    }
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
        robot.setVector(robot.getVector().add(new Vector(Adjacent, Opposite)));
        sensor.refresh();
    }
    
    /**
     * Saves a turning move of the robot.
     * @param turn is an object of the type Turn. 
     */
    public void turningRobot(Turn turn){
        robot.setAngle(robot.getAngle() + turn.getAngle());
        sensor.refresh();
        sensor.setAngle(sensor.getAngle() + turn.getAngle());
    }
    
    /**
     * Saves a turning move of the sensor.
     * @param Angle is the angle the sensor turned saved in a double.
     */
    public void turningSensor(double Angle){
        sensor.setAngle(sensor.getAngle() + Angle);
    }
}
