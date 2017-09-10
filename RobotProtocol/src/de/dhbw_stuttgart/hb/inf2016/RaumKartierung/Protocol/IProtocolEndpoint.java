package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

public interface IProtocolEndpoint {
	public void addCommandListener(ICommandReceiver receiver);

	public IToRobotSender getToRobotSender();

	public IFromRobotSender getFromRobotSender();

	public boolean isConnected();

	public void removeCommandListener(ICommandReceiver receiver);

	public void sendCommand(CommandBase cmd);
	
	public void addSendCommandInterceptorListener(ICommandReceiver handler);
	
	public void removeSendCommandInterceptorListener(ICommandReceiver handler);
}
