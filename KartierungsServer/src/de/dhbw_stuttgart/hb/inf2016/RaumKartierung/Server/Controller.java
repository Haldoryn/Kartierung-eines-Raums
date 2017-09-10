package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Config.Config;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Controlling;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Forward;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling.Move;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.VectorRoom;

public class Controller {
    private Move nextMove;
    private Config config;
    private VectorRoom vectorRoom = new VectorRoom();
    private Controlling controlling;
    private int timesScaned;

    
    
    public Controller(Config config) {
		super();
		this.config = config;
		controlling=new Controlling(config);
	}
	// here all stuff with moving and sends commands to robo
    public void doMove(){
        //return if GUI switch is off
        nextMove = controlling.next(); //getting next move
        //Send nextMove.leftMotor and nextMove.rightMotor to Robot.
    }
    // decides what will be done after a move
    public void moveDone(){ //if robot finished moving this method gets called
        vectorRoom.movingRobot(nextMove); //saving move in vectorRoom
        if(nextMove instanceof Forward){
            timesScaned = 0;
            //let robot scan
        }
        else{
            doMove();
        }
    }
    // method doMeasureing(return ultrasonic value + gyro value
    
    // gets called after each scan. Evaluates the Scan result
    public void scanDone(double distance){ // starts with finished scan
        vectorRoom.setScan(distance);

        if(timesScaned >= (int)config.getConstbyName("maxScans")){
            //turn robots scanner by cons.getConstbyName("AnglePerScan") * cons.getConstbyName("maxScans") / 2 in order to get the scanner to his original position.
            vectorRoom.turningSensor((int)config.getConstbyName("AnglePerScan"));
            doMove();
        }
        else if(timesScaned > (int)config.getConstbyName("maxScans") / 2){
            //turn robots scanner by - cons.getConstbyName("AnglePerScan")
            vectorRoom.turningSensor(-(int)config.getConstbyName("AnglePerScan"));
            //let robot scan
        }
        else if(timesScaned == (int)config.getConstbyName("maxScans") / 2){
            //turn robots scanner by -(cons.getConstbyName("AnglePerScan") * cons.getConstbyName("maxScans") / 2 + cons.getConstbyName("AnglePerScan")) in order to turn the scanner back to the other side
            vectorRoom.turningSensor(-((int)config.getConstbyName("AnglePerScan") * (int)config.getConstbyName("maxScans") / 2 + (int)config.getConstbyName("AnglePerScan")));
            //let robot scan
        }
        else{
            //turn robots scanner by cons.getConstbyName("AnglePerScan")
            vectorRoom.turningSensor((int)config.getConstbyName("AnglePerScan"));
            //let robot scan
        }
    }
}

