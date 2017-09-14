package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

/**Event listener interface for receiving commands
 * @author JVogel
 *
 */
public interface ICommandReceiver {
	/**Is invoked then a new command was received.
	 * @param cmd The received command.
	 */
	void commandReceived(CommandBase cmd);
}
