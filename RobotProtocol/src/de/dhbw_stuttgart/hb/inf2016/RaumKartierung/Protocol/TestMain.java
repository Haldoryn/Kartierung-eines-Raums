package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;

import javax.sound.midi.ControllerEventListener;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.*;

public class TestMain {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

	}

	public void ControlerEndpointExample() throws InstantiationException, IllegalAccessException, IOException {
		ControlerEndpoint controlerEnd = new ControlerEndpoint();
		controlerEnd.connect(InetAddress.getByName("192.168.0.2"), 9999);;

		controlerEnd.addOnDisconnectListener(new IDisconnectedEventListener() {

			@Override
			public void OnDisconnect() {
				System.out.println("Controller has disconnected");
			}
		});

		controlerEnd.addCommandListener(new ICommandReceiver() {

			@Override
			public void commandReceived(CommandBase cmd) {
				System.out.println("Command from controller received: " + cmd.getType().toString());
			}
		});
		
	
		IFromRobotSender robot = controlerEnd.getFromRobotSender();
		
		//Tell the controler thatthe reset was completed.
		robot.sendReturnReset();
		
		IToRobotSender controler = controlerEnd.getToRobotSender();
		
		//Reset the robot
		controler.sendReset();
		
		//Request the robot status
		controler.sendGetStatus();
		
		//Move the robot for 2 seconds(360° on both motors total)
		controler.sendMoveMotor(180, 180, 360, 360);
	}
}
