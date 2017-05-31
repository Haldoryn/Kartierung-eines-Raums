package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

public class TestMain {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		CommandParser parser = new CommandParser();
		String commandString = new MoveMotorCmd(50,50,100,100).toString();
		
		CommandBase cmd = parser.Parse(commandString);
		
		System.out.println(cmd);
	}

}
