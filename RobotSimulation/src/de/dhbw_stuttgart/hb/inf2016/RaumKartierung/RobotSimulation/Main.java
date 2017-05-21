package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		if(args.length >0 )
		{
			if(Arrays.asList(args).contains("/console"))
			{
				//Start in console mode.
			}
		}
		else
		{
			//Launch in ui mode.
		}
	}

}
