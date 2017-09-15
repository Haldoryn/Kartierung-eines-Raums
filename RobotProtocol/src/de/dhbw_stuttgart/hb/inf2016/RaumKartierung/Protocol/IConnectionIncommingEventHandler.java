package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.net.Socket;

/**Event listener interface for events that inform about incoming connections.
 * @author JVogel
 *
 */
public interface IConnectionIncommingEventHandler {
	/**Is called when a new connection is incoming.
	 * @param sock The socket of the new connection.
	 */
	public void OnNewConnection(Socket sock);
}
