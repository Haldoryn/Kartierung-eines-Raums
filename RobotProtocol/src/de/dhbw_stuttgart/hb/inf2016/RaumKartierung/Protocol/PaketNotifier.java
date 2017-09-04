package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands.CommandBase;

class PaketNotifier {
	private CommandBase value;
	private Lock lock = new ReentrantLock();

	public CommandBase getValue() {
		lock.lock(); // block until condition holds
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}

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
