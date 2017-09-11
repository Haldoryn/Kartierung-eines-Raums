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
	 * cons is the object, that reads the constants xml file and returns the wanted constant. 
	 * It needs the path of the xml file as the parameter in the constructor. 
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
		//ReturnUltrasonicCmd returnUltrasonic= robotSender.sendGetUltrasonicAndWait((int)config.getConstbyName("timeout"));
		//Double ScanValue = returnUltrasonic.getValue();
		nextMove = controlling.next(/*ScanValue*/);
	
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
		Double ScanValue = returnUltrasonic.getValue();
		if(ScanValue >(int)config.getConstbyName("minScanDist") && ScanValue <(int)config.getConstbyName("maxScanDist")) {
			vectorRoom.setScan(returnUltrasonic.getValue());
		}
		
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
	
	/**
	 * This method checks, if the robot can move to the place he wants to move. 
	 * It scans in front of itself and it scans in an angle in order to check if it fits thru.
	 * @return A boolean. If the robot can fit this method returns true. If not it returns false.
	 * @throws InterruptedException
	 */
	public boolean checkScan() throws InterruptedException {
		/*
		 * The Robot scans in front of him.
		 */
		ReturnUltrasonicCmd returnUltrasonic= robotSender.sendGetUltrasonicAndWait((int)config.getConstbyName("timeout"));
		Double ScanValue = returnUltrasonic.getValue();
		/*
		 * It gets checked, if there is space for the robot to move. If not it returns false. 
		 */
		if(ScanValue < (int)config.getConstbyName("distancePerMove"))
			return false;
		/*
		 * The Robot turns its head in the angle tan((width/2)/distancePerMove) in order to scan if the right side would fit.
		 */
		robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), (int)Math.ceil(Math.tan(((double)config.getConstbyName("width")/2)/(double)config.getConstbyName("distancePerMove"))), (int)config.getConstbyName("timeout"));
		/*
		 * The robot scans 
		 */
		returnUltrasonic= robotSender.sendGetUltrasonicAndWait((int)config.getConstbyName("timeout"));
		ScanValue = returnUltrasonic.getValue();
		/*
		 * It gets calculated if the robot would fit.
		 */
		if(ScanValue < Math.sqrt(Math.pow((double)config.getConstbyName("width")/2, 2) + Math.pow((double)config.getConstbyName("distancePerMove"),2))) {
			/*
			 * If it does not fit, the sensor gets turned back again and the method returns false.
			 */
			robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), -((int)Math.ceil(Math.tan(((double)config.getConstbyName("width")/2)/(double)config.getConstbyName("distancePerMove")))), (int)config.getConstbyName("timeout"));
			return false;
		}
		/*
		 * The robot turns its sensor in the angle -2 * tan((width/2)/distancePerMove) in order to rotate the scanner back to its previous position and further to the position, were it can scan if it would fit.
		 */
		robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), -2 * ((int)Math.ceil(Math.tan(((double)config.getConstbyName("width")/2)/(double)config.getConstbyName("distancePerMove")))), (int)config.getConstbyName("timeout"));
		/*
		 * the robot scans
		 */
		returnUltrasonic= robotSender.sendGetUltrasonicAndWait((int)config.getConstbyName("timeout"));
		ScanValue = returnUltrasonic.getValue();
		/*
		 * It gets calculated if the robot would fit.
		 */
		if(ScanValue < Math.sqrt(Math.pow((double)config.getConstbyName("width")/2, 2) + Math.pow((double)config.getConstbyName("distancePerMove"),2))) {
			/*
			 * If it does not fit, the sensor gets turned back again and the method returns false.
			 */
			robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), (int)Math.ceil(Math.tan(((double)config.getConstbyName("width")/2)/(double)config.getConstbyName("distancePerMove"))), (int)config.getConstbyName("timeout"));
			return false;
		}
		/*
		 * If the robot fits it turns its sensor back and returns true.
		 */
		robotSender.sendMoveSensorAndWait((int)config.getConstbyName("SensorMoveSpeed"), (int)Math.ceil(Math.tan(((double)config.getConstbyName("width")/2)/(double)config.getConstbyName("distancePerMove"))), (int)config.getConstbyName("timeout"));
		return true;
	}
}




















