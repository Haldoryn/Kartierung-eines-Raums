package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ControlerEndpoint;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.ICommandReceiver;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandType;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Constants.Constants;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Controlling;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;
/**
 * 
 * @author Samuel Volz
 *
 */
public class RobotInteractionHandler {
	/*
	 * measuredDistance saves the distance from the last scan.
	 */
	private double measuredDistance;
	
	/*
	 * rachedDistanceLeftMotor saves the last driven distance of the left motor.
	 */
	private double reachedDistanceLeftMotor;
	
	/*
	 * rachedDistanceRightMotor saves the last driven distance of the right motor.
	 */
	private double reachedDistanceRightMotor;
	
	/*
	 * timesScaned is a counter of every scan the robot makes. It counts only the scans of one sweep.
	 */
	private int timesScaned;
	
	/*
	 * cons is the object, that reads the constants xml file and returns the wanted constant. Id needs the path of the xml file as the parameter in the constructor. 
	 */
	private Constants cons;//temp path must be replaced with real path
	
	/*
	 * vectorRoom is the object, that saves all points. It needs the movement of the robot in order to calculate the scanned points.
	 */
	private VectorRoom vectorRoom = new VectorRoom();
	
	/*
	 * Controlling is responsible for the movement of the robot.
	 */
	private Controlling controlling;
	
	/*
	 * Endpoint is the facade of the robot protocol.
	 */
	private ControlerEndpoint endpoint;
	
	/*
	 * robotSender sends all commands to the robot.
	 */
	private IToRobotSender robotSender;
	
	/*
	 * nextMove saves the next move of the robot.
	 */
	private Move nextMove;
	
	public RobotInteractionHandler() throws InstantiationException, IllegalAccessException, UnknownHostException, IOException {
		buildConnection();
		robotSender = endpoint.getToRobotSender();
	}
	/**
	 * It calls the and goes thru the procedure of controlling and communicating with the robot.
	 * @throws InterruptedException
	 */
	public void doMove() throws InterruptedException {
		/* 
		 * at the Start of the loop the program gets the new move the robot has to perform. 
		 */
		nextMove = controlling.next();
	
		/*
		 * The speed of both the Motors and the distance both motors have to drive get send to the robot.
		 * It also gets the time, the protocol waits for a message from the robot till it stops waiting.
		 */
		robotSender.sendMoveMotorAndWait(
				(int)cons.getConstbyName("speed"), 
				(int)cons.getConstbyName("speed"), 
				(int)Math.floor(nextMove.getLeftMotor()), 
				(int)Math.floor(nextMove.getRightMotor()), 
				(int)cons.getConstbyName("timeout")
		);
		/*
		 * the nextMove gets updated with the informations the robot returned and the move gets send to the vectorRoob 
		 * in order to save the move and calculate the location of the robot.
		 */
		nextMove.setMotor(reachedDistanceLeftMotor, reachedDistanceRightMotor);
		vectorRoom.movingRobot(nextMove);
		
		/*
		 * If the last move was a move forward and not a turn the robot should mace a scan sweep.
		 * The timesScanned counter gets reseted and the scan method gets called. 
		 */
		if(nextMove instanceof Forward) {
			timesScaned = 0;
			scan();
		}
	}
	
	/**
	 * buildConnection tries to build a connection with the robot. 
	 * It also sets listeners on returnUltrasonic and returnMotor in order to get the informations the robot returns. 
	 * Oh and it throws a lot of stuff because it just ignores every exception. 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void buildConnection() throws InstantiationException, IllegalAccessException, UnknownHostException, IOException {
		endpoint = new ControlerEndpoint();
		/*
		 * A listener get set on returnUltrasonic to save the return value in the variable measuredDistance.
		 * A listener get set on returnMotor to save the return values in the variables reachedDistanceLeftMotor and reachedDistanceRightMotor.
		 */
		endpoint.addCommandListener(new ICommandReceiver() {
			@Override
			public void commandReceived(CommandBase cmd) {
				CommandType type = cmd.getType();
				switch(type) {
				case returnUltrasonic:
					measuredDistance = Integer.parseInt(cmd.toString().split(",")[1].replace(";", ""));
					break;
				case returnMotor:
					reachedDistanceLeftMotor = Double.parseDouble(cmd.toString().split(",")[1]);
					reachedDistanceRightMotor = Double.parseDouble(cmd.toString().split(",")[2].replace(";", ""));
					break;
				}
			}
		});
		
		/*
		 *The connection gets build with the IP and the Port of the Robot.  
		 */
		endpoint.connect(InetAddress.getByName((String)cons.getConstbyName("RobotIP")), (int)cons.getConstbyName("RobotPort"));	
	}
	
	/**
	 * This method goes thru the procedure of a sweep. Call it once and it runs a full sweep. It saves all the scans in the vectorRoom object.
	 * @throws InterruptedException
	 */
	public void scan() throws InterruptedException {
		/*
		 * It gets counted, how many times the robot did scans in this sweep.
		 * Eventually this has to be moved to the end of this method.
		 */
		timesScaned++;
		
		/*
		 * The robot gets called to do a scan. This method waits till the robot returned something or till the timeout runs out. 
		 */
		robotSender.sendGetUltrasonicAndWait((int)cons.getConstbyName("timeout"));
		
		/*
		 * The results of the scan get saved in the veectorRoom. 
		 */
		vectorRoom.setScan(measuredDistance);
		
        if(timesScaned >= (int)cons.getConstbyName("maxScans")){
        	/*
        	 * If the robot did all the scans he needs he needs to move the sensor to its original position.
        	 * This sensor move gets saved in the vectorRoom.
        	 */
        	robotSender.sendMoveSensorAndWait((int)cons.getConstbyName("SensorMoveSpeed"), (int)cons.getConstbyName("AnglePerScan") * (int)cons.getConstbyName("maxScans") / 2, (int)cons.getConstbyName("timeout"));
            vectorRoom.turningSensor((int)cons.getConstbyName("AnglePerScan") * (int)cons.getConstbyName("maxScans") / 2);
        }
        else if(timesScaned > (int)cons.getConstbyName("maxScans") / 2){
        	/*
        	 * If the robot did over the half of all the scans he needs, he turns his sensor to the other direction.
        	 * This sensor turning move gets saved in the vectorRoom.
        	 * The scan method gets called again to make the next scan.
        	 */
        	robotSender.sendMoveSensorAndWait((int)cons.getConstbyName("SensorMoveSpeed"), -(int)cons.getConstbyName("AnglePerScan"), (int)cons.getConstbyName("timeout"));
            vectorRoom.turningSensor(-(int)cons.getConstbyName("AnglePerScan"));
            scan();
        }
        else if(timesScaned == (int)cons.getConstbyName("maxScans") / 2){
        	/*
        	 * If the robot did the half of its scans it turns its sensor back to its neutral position and turns it in the other direction.
        	 * This sensor move gets saved in the vectorRoom.
        	 * The scan method gets called again to make the next scan.
        	 */
        	robotSender.sendMoveSensorAndWait((int)cons.getConstbyName("SensorMoveSpeed"), -(int)cons.getConstbyName("AnglePerScan") * (int)cons.getConstbyName("maxScans") / 2, (int)cons.getConstbyName("timeout"));
            vectorRoom.turningSensor(-((int)cons.getConstbyName("AnglePerScan") * (int)cons.getConstbyName("maxScans") / 2 + (int)cons.getConstbyName("AnglePerScan")));
            scan();
        }
        else{
        	/*
        	 * If the robot did oder the half of its scans, it rotates its scanner.
        	 * This sensor move gets saved in the vectorRoom.
        	 * The scan method gets called again to make the next scan.
        	 */
        	robotSender.sendMoveSensorAndWait((int)cons.getConstbyName("SensorMoveSpeed"), (int)cons.getConstbyName("AnglePerScan"), (int)cons.getConstbyName("timeout"));
            vectorRoom.turningSensor((int)cons.getConstbyName("AnglePerScan"));
            scan();
        }
	}
}
