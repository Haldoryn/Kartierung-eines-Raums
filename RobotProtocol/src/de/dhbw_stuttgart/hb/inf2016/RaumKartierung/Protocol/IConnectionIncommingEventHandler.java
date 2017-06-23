package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.net.Socket;

public interface IConnectionIncommingEventHandler {
	public void OnNewConnection(Socket sock);
}
