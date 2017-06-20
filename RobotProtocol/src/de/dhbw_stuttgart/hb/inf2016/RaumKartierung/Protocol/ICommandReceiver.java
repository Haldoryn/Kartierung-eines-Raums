package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

public interface ICommandReceiver {
	 void commandReceived(CommandBase cmd);
}
