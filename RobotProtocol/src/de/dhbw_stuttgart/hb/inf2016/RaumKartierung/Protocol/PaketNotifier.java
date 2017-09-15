package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

/**Class that is used to wait for a command response.
 * @author JVogel
 *
 */
class PaketNotifier {
	private CommandBase value;
	private Lock lock = new ReentrantLock();

	/**Gets the response value
	 * @return The response value.
	 */
	public CommandBase getValue() {
		lock.lock(); // block until condition holds
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}

	/**Sets the response value.
	 * @param value The response value.
	 */
	public void setValue(CommandBase value) {
		if(value == null)
		{
			throw new IllegalArgumentException("Argument 'value' must not be null");
		}
		lock.lock(); // block until condition holds
		this.value = value;
		lock.unlock();
	}

}
