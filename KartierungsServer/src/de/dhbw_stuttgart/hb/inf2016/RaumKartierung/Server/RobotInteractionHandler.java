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
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Turn;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Robot;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.RobotPositionHandler;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Sensor;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.Vector;
/**
 * The robot interaction handler id the class which interacts with the robot. 
 * It gets the next move and sends it to the robot. 
 * This class also send all moves to the vectorroom in order to save all moves.
 * @author Samuel Volz
 *
 */
public class RobotInteractionHandler {
	/**
	 * timesScaned is a counter of every scan the robot makes. It counts only the scans of one sweep.
	 */
	private int timesScaned;
	/**
	 * cons is the object, that reads the constants xml file and returns the wanted constant.
	 */
	private Config config;
	/**
	 * vectorRoom is the object, that saves all points. It needs the movement of the robot in order to calculate the scanned points.
	 */
	private VectorRoom vectorRoom;
	/**
	 * Controlling is responsible for the movement of the robot.
	 */
	private Controlling controlling; 
	/**
	 * robotSender sends all commands to the robot.
	 */
	private IToRobotSender robotSender;
	private Move nextMove;
	private int speed; 
	private int timeout;
	/**
	 * minScanDist is the minimum a scan distance can be before the scan gets trashed.
	 */
	private double minScanDist; 
	/**
	 * maxScanDist is the maximum a scan distance can be before the scan gets trashed.
	 */
	private double maxScanDist; 
	private int sensorSpeed; 
	private int anglePerScan; 
	private int maxScans; 
	/**
	 * width is the width of the robot. This class needs it in order to check if the robot can fit thru a checked space.
	 */
	private double width;
	private double distance;
	/** 
	 * Robot saves the position and the angle of the robot.
	 */
	private Robot robot;
	/**
	 * Sensor saves the position and the angle of the sensor.
	 */
	private Sensor sensor;
	/**
	 * The robotPositionHandler calculates the positions of the robot and sensor.
	 */
	private RobotPositionHandler robotPositionHandler;
	
	/**
	 * Creates the RobotinteractionHandler object. 
	 * @param endpoint 
	 * @param config
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
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
		this.config = config;
		controlling = new Controlling(config);
		speed  = (int)config.getConstbyName("speed");
		timeout = (int)config.getConstbyName("timeout");
		minScanDist = (double)config.getConstbyName("minScanDist");
		maxScanDist = (double)config.getConstbyName("maxScanDist");
		sensorSpeed = (int)config.getConstbyName("SensorMoveSpeed");
		anglePerScan = (int)config.getConstbyName("AnglePerScan");
		maxScans = (int)config.getConstbyName("maxScans");
		width = (double)config.getConstbyName("wheelDistance");
		distance = (double)config.getConstbyName("distancePerMove");
		robot = new Robot(new Vector(0,0), 0);
		sensor = new Sensor(robot, config);
		robotPositionHandler = new RobotPositionHandler(robot, sensor);
		vectorRoom = new VectorRoom(sensor);

	}
	/**
	 * @return vectorRoom
	 */
	public VectorRoom getVectorRoom() {
		return vectorRoom;
	}
	
	
	/**
	 * Gets the robot data object
	 * @return The robot data object
	 */
	public Robot getRobot()
	{
		return robot;
	}
	
	/**
	 * Get the sensor data object.
	 * @return The sensor data object.
	 */
	public Sensor getSensor()
	{
		return sensor;
	}
	
	/**
	 * This class goes thru the procedure of controlling and communicating with the robot.
	 * @throws InterruptedException
	 */
	public void doMove() throws InterruptedException {	
		// at the Start of the loop the program gets the new move the robot has to perform. 
		nextMove = controlling.next(checkScan());
		
		// If the last move was a move forward and not a turn the robot should mace a scan sweep.
		// The timesScanned counter gets reseted and the scan method gets called. 
		if(nextMove instanceof Forward) {
			timesScaned = 0;
			scan();
		}
				 	
		// The speed of both the Motors and the distance both motors have to drive get send to the robot.
		// It also gets the time, the protocol waits for a message from the robot till it stops waiting.
		ReturnMotorCmd returnMotor= robotSender.sendMoveMotorAndWait(
				speed, 
				speed, 
				(int)Math.floor(nextMove.getLeftMotor()), 
				(int)Math.floor(nextMove.getRightMotor()), 
				timeout
		);
		
		// the nextMove gets updated with the informations the robot returned and the move gets send to the vectorRoob 
	 	// in order to save the move and calculate the location of the robot.
		if(nextMove instanceof Forward)
			((Forward)nextMove).setMotor(returnMotor.getReachedLeftDistanceAngle(), returnMotor.getReachedRightDistanceAngle());
		else if(nextMove instanceof Turn)
			((Turn) nextMove).setAngle(robotSender.sendGetGyroscopeAndWait(timeout).getValue());
		robotPositionHandler.movingRobot(nextMove);
	}
	
	/**
	 * This method goes thru the procedure of a sweep. Call it once and it runs a full sweep. It saves all the scans in the vectorRoom object.
	 * @throws InterruptedException
	 */
	public void scan() throws InterruptedException {
	
		
		// The robot gets called to do a scan. This method waits till the robot returned something or till the timeout runs out. 
		ReturnUltrasonicCmd returnUltrasonic= robotSender.sendGetUltrasonicAndWait(timeout);
		Double ScanValue = returnUltrasonic.getValue();
		
		// It gets counted, how many times the robot did scans in this sweep.
		timesScaned++;
		
		// The results of the scan get saved in the veectorRoom if the scan value is in the trustworthy range. 
		if(ScanValue > minScanDist && ScanValue < maxScanDist) {
			vectorRoom.setScan(returnUltrasonic.getValue());
		}
		
        if(timesScaned >= maxScans){
        	// If the robot did all the scans he needs he needs to move the sensor to its original position.
        	// This sensor move gets saved in the vectorRoom.
        	robotSender.sendMoveSensorAndWait(sensorSpeed, anglePerScan * maxScans / 2, timeout);
            robotPositionHandler.turningSensor(anglePerScan * maxScans / 2);
        }
        else if(timesScaned > maxScans / 2){
        	// If the robot did over the half of all the scans he needs, he turns his sensor to the other direction.
        	// This sensor turning move gets saved in the vectorRoom.
        	// The scan method gets called again to make the next scan.
        	robotSender.sendMoveSensorAndWait(sensorSpeed, -anglePerScan, timeout);
            robotPositionHandler.turningSensor(-anglePerScan);
            scan();
        }
        else if(timesScaned == maxScans / 2){
        	// If the robot did the half of its scans it turns its sensor back to its neutral position and turns it in the other direction.
        	// This sensor move gets saved in the vectorRoom.
        	// The scan method gets called again to make the next scan.
        	robotSender.sendMoveSensorAndWait(sensorSpeed, -anglePerScan * maxScans / 2, timeout);
            robotPositionHandler.turningSensor(-anglePerScan * maxScans / 2);
            scan();
        }
        else{
        	// If the robot did oder the half of its scans, it rotates its scanner.
        	// This sensor move gets saved in the vectorRoom.
        	// The scan method gets called again to make the next scan.
        	robotSender.sendMoveSensorAndWait(sensorSpeed, anglePerScan, timeout);
            robotPositionHandler.turningSensor(anglePerScan);
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
		double checkdistance = distance +16;
		// hypotenuse is the hypotenuse of the triangle distance and width/2. 
		// The hypotenuse is needed in order to check if the scan length is long enough for the robot to fit.
		// This constant is calculated by the formula root((width/2)^2 + distance^2.
		double hypotenuse = Math.sqrt(Math.pow(width/2, 2) + Math.pow(checkdistance,2));
		
		// angle is the angle between hypotenuse and distance in the triangle distance width and hypotenuse.
		// This angle is needed in order to turn the sensor to this angle in order to scan and measure the hypotenuse.
		// This constant is calculated by the formula tan((width/2)/distance)
		// The double calculated by this formula gets rounded up in order to get a Integer because the robot needs its turning angle in an Integer. 
		int angle = (int)Math.ceil(Math.tan((width/2)/checkdistance));
		
		// The Robot scans in front of him.
		ReturnUltrasonicCmd returnUltrasonic= robotSender.sendGetUltrasonicAndWait(timeout);
		Double ScanValue = returnUltrasonic.getValue();
		
		// It gets checked, if there is space for the robot to move. If not it returns false. 
		if(ScanValue < checkdistance)
			return false;
		
		// The Robot turns its head in the angle tan((width/2)/distancePerMove) in order to scan if the right side would fit.
		robotSender.sendMoveSensorAndWait(sensorSpeed, angle, timeout);
		
		// The robot scans 
		returnUltrasonic= robotSender.sendGetUltrasonicAndWait(timeout);
		ScanValue = returnUltrasonic.getValue();
		
		// It gets calculated if the robot would fit.
		if(ScanValue < hypotenuse) {
			
			// If it does not fit, the sensor gets turned back again and the method returns false.
			robotSender.sendMoveSensorAndWait(sensorSpeed, -angle, timeout);
			return false;
		}
		// The robot turns its sensor in the angle -2 * tan((width/2)/distancePerMove) in order to rotate the scanner 
		// back to its previous position and further to the position, were it can scan if it would fit.
		robotSender.sendMoveSensorAndWait(sensorSpeed, -2 * angle, timeout);
		
		// the robot scans
		returnUltrasonic= robotSender.sendGetUltrasonicAndWait(timeout);
		ScanValue = returnUltrasonic.getValue();
		
		// It gets calculated if the robot would fit.
		if(ScanValue < hypotenuse) {
			// If it does not fit, the sensor gets turned back again and the method returns false.
			robotSender.sendMoveSensorAndWait(sensorSpeed, angle, timeout);
			return false;
		}
		
		// If the robot fits it turns its sensor back and returns true.
		robotSender.sendMoveSensorAndWait(sensorSpeed, angle, timeout);
		return true;
	}
}