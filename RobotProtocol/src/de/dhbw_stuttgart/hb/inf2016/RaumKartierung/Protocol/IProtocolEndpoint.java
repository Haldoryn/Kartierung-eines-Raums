package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

public interface IProtocolEndpoint {
	public void addCommandListener(ICommandReceiver receiver);

	public void removeCommandListener(ICommandReceiver receiver);

	public void sendCommand(CommandBase cmd);
	
	public IRobot getRobotInterface();
	
	public IControler getControlerInterface();
	
	public boolean isConnected();
}
