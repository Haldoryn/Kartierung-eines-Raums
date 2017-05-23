package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.RobotSimulation;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class StringEventOutputStream extends PrintStream {

	private List<StringOutputListener> listeners = new ArrayList<>();
	private Object sync = new Object();

	public StringEventOutputStream() {
		super(System.out);
	}

	@Override
	public void print(Object obj) {
		super.print(obj);
		for (StringOutputListener listener : listeners) {
			listener.handleString(obj.toString());
		}
	}

	@Override
	public void println(String obj) {
		String val = obj + System.lineSeparator();
		for (StringOutputListener listener : listeners) {
			listener.handleString(val);
		}
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		super.printf(format, args);
		return this;
	}

	@Override
	public void println(Object args) {
		super.println(args);
		String val = args.toString() + System.lineSeparator();
		for (StringOutputListener listener : listeners) {
			listener.handleString(val);
		}
	}

	public void AddListener(StringOutputListener listener) {
		synchronized (sync) {
			listeners.add(listener);
		}
	}

	public void RemoveListener(StringOutputListener listener) {
		synchronized (sync) {
			listeners.remove(listener);
		}
	}
}
