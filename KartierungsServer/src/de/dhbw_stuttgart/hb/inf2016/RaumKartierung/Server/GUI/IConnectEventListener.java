package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.net.InetAddress;

/**
 * The event listener for the connect button.
 * @author Chris
 *
 */
public interface IConnectEventListener {
 public void onConnect(InetAddress ip,int port);
}
