package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ControlerEndpoint extends EndpointBase {

	private boolean run;
	
	protected ControlerEndpoint() throws InstantiationException, IllegalAccessException {
		super();
	}
	
	public void connect(InetAddress ip,int port)
	{
	
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

}
