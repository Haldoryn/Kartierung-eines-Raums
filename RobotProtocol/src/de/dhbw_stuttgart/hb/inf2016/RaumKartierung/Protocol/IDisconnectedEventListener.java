package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

/**
 * Event listener interface for events that inform about the end of a
 * connection.
 * 
 * @author JVogel
 *
 */
public interface IDisconnectedEventListener {
	/**
	 * Called when a connection was disconnected.
	 * 
	 */
	public void OnDisconnect();
}
