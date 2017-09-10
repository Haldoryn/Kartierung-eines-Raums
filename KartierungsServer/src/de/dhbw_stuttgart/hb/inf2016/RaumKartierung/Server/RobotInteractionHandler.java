package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import java.io.IOException;
import java.net.UnknownHostException;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IProtocolEndpoint;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.IToRobotSender;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.ReturnMotorCmd;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.ReturnUltrasonicCmd;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;
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
	 * timesScaned is a counter of every scan the robot makes. It counts only the scans of one sweep.
	 */
	private int timesScaned;
	
	/*
	 * cons is the object, that reads the constants xml file and returns the wanted constant. Id needs the path of the xml file as the parameter in the constructor. 
	 */
	private Config config;
	
	/*
	 * vectorRoom is the object, that saves all points. It needs the movement of the robot in order to calculate the scanned points.
	 */
	private VectorRoom vectorRoom = new VectorRoom();
	
	/*
	 * Controlling is responsible for the movement of the robot.
	 */
	private Controlling controlling;


	/*
	 * robotSender sends all commands to the robot.
	 */
	private IToRobotSender robotSender;
	
	/*
	 * nextMove saves the next move of the robot.
	 */
	private Move nextMove;
	
	public RobotInteractionHandler(IProtocolEndpoint endpoint,Config config) throws InstantiationException, IllegalAccessException, UnknownHostException, IOException {
		if(endpoint == null)
		{
			throw new IllegalArgumentException("The 'endpoint' argument must not be null.");
		}
		if(config == null)
		{
			throw new IllegalArgumentException("The 'config' argument must not be null.");
		}
				
		this.robotSender = endpoint.getToRobotSender();
		this.config=config;
		 controlling= new Controlling(config);
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
		ReturnMotorCmd returnMotor= robotSender.sendMoveMotorAndWait(
				(int)config.getConstbyName("speed"), 
				(int)config.getConstbyName("speed"), 
				(int)Math.floor(nextMove.getLeftMotor()), 
				(int)Math.floor(nextMove.getRightMotor()), 
				(int)config.getConstbyName("timeout")
		);
		/*
		 * the nextMove gets updated with the informations the robot returned and the move gets send to the vectorRoob 
		 * in order to save the move and calculate the location of the robot.
		 */
		nextMove.setMotor(returnMotor.getReachedLeftDistanceAngle(), returnMotor.getReachedRightDistanceAngle());
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
	public VectorRoom getVectorRoom() {
		return vectorRoom;
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
		ReturnUltrasonicCmd returnUltrasonic= robotSender.sendGetUltrasonicAndWait((int)config.getConstbyName("timeout"));
		
		/*
		 * The results of the scan get saved in the veectorRoom. 
		 */
		vectorRoom.setScan(returnUltrasonic.getValue());
		
        if(timesScaned >= (int)config.getConstbyName("maxScans")){
        	/*
        	 * If the robot did all the scans he needs he needs to move the sensor to its original position.
        	 * This sensor move gets saved in the vectorRoom.
        	 */
        	robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), (int)config.getConstbyName("AnglePerScan") * (int)config.getConstbyName("maxScans") / 2, (int)config.getConstbyName("timeout"));
            vectorRoom.turningSensor((int)config.getConstbyName("AnglePerScan") * (int)config.getConstbyName("maxScans") / 2);
        }
        else if(timesScaned > (int)config.getConstbyName("maxScans") / 2){
        	/*
        	 * If the robot did over the half of all the scans he needs, he turns his sensor to the other direction.
        	 * This sensor turning move gets saved in the vectorRoom.
        	 * The scan method gets called again to make the next scan.
        	 */
        	robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), -(int)config.getConstbyName("AnglePerScan"), (int)config.getConstbyName("timeout"));
            vectorRoom.turningSensor(-(int)config.getConstbyName("AnglePerScan"));
            scan();
        }
        else if(timesScaned == (int)config.getConstbyName("maxScans") / 2){
        	/*
        	 * If the robot did the half of its scans it turns its sensor back to its neutral position and turns it in the other direction.
        	 * This sensor move gets saved in the vectorRoom.
        	 * The scan method gets called again to make the next scan.
        	 */
        	robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), -(int)config.getConstbyName("AnglePerScan") * (int)config.getConstbyName("maxScans") / 2, (int)config.getConstbyName("timeout"));
            vectorRoom.turningSensor(-((int)config.getConstbyName("AnglePerScan") * (int)config.getConstbyName("maxScans") / 2 + (int)config.getConstbyName("AnglePerScan")));
            scan();
        }
        else{
        	/*
        	 * If the robot did oder the half of its scans, it rotates its scanner.
        	 * This sensor move gets saved in the vectorRoom.
        	 * The scan method gets called again to make the next scan.
        	 */
        	robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), (int)config.getConstbyName("AnglePerScan"), (int)config.getConstbyName("timeout"));
            vectorRoom.turningSensor((int)config.getConstbyName("AnglePerScan"));
            scan();
        }
	}
}
