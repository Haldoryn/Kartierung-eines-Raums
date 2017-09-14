package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

/**Interface for the endpoints of the protocol
 * @author JVogel
 *
 */
public interface IProtocolEndpoint {
	/**Adds a listener for receiving commands.
	 * @param receiver
	 */
	public void addCommandListener(ICommandReceiver receiver);

	/**Gets the instance of the IToRobotSender interface. That can be used to send commands to the robot.
	 * @return The instance of the IToRobotSender interface.
	 */
	public IToRobotSender getToRobotSender();

	/**Gets the instance of the IFromRobotSender interface. That can be used to send commands from the robot.
	 * @return The instance of the IFromRobotSender interface.
	 */
	public IFromRobotSender getFromRobotSender();

	/**Get a value indicating whether the endpoint is connected or not.
	 * @return
	 */
	public boolean isConnected();

	/**Removes a command listener.
	 * @param receiver The listener that should be removed.
	 */
	public void removeCommandListener(ICommandReceiver receiver);

	/**Sends a command to the other endpoint that this endpoint is connected to.
	 * @param cmd
	 */
	public void sendCommand(CommandBase cmd);
	
	/**Adds a listener that informs about all outgoing commands.
	 * @param handler The listener.
	 */
	public void addSendCommandInterceptorListener(ICommandReceiver handler);
	
	/**Removes a SendCommandInterceptorListener.
	 * @param handler The listner that should be removed.
	 */
	public void removeSendCommandInterceptorListener(ICommandReceiver handler);
}
