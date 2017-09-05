package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.Controlling;

import com.sun.javafx.scene.layout.region.Margins;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;

import java.util.Random;

public class Controlling {
    Move NextMove;
    // int scanresult

    public Move next() {
        if(NextMove == null || NextMove instanceof Turn){
        	
        	// if ultrasonicScan < distance + securityDistance
            NextMove = new Forward(500 /*TEMP*/); // how to set the distance? 
            return NextMove;
        }
        else if(NextMove instanceof Forward){
            Random random = new Random();
            NextMove = new Turn(random.nextInt(360 * 2) - 360);
            // return gyro Value
            return NextMove;
        }
        else{
            return null;
        }
    }
}
