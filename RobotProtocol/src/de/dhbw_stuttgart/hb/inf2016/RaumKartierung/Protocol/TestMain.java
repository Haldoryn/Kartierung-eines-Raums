package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

public class TestMain {

	public static void main(String[] args) throws IOException {

		List<CommandBase> cmds = new ArrayList<>();
		cmds.add(new MoveMotorCmd());
		cmds.add(new MoveSensorCmd());
		cmds.add(new GetUltrasonicCmd());
		cmds.add(new GetGyroscopeCmd());
		cmds.add(new ResetCmd());
		cmds.add(new ReturnUltrasonicCmd());
		cmds.add(new ReturnGyroscopeCmd());
		cmds.add(new ReturnMotorCmd());
		cmds.add(new ReturnSensorCmd());
		cmds.add(new ReturnStatusCmd());
		
		for (CommandBase cmd : cmds) {
			System.out.println(cmd);
		}
	}

}
