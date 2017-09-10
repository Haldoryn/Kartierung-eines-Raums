package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.net.InetAddress;

public interface IConnectEventListener {
 public void onConnect(InetAddress ip,int port);
}
