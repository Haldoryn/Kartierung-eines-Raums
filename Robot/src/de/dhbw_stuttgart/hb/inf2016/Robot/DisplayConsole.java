package de.dhbw_stuttgart.hb.inf2016.Robot;

import java.io.PrintStream;

import lejos.hardware.lcd.LCDOutputStream;

/**
 * Class for redirecting the the console output to the display.
 * 
 * @author JVogel
 *
 */
public class DisplayConsole {

	/**
	 * Initialize the output
	 */
	public static void Init() {
		System.setOut(new PrintStream(new LCDOutputStream()));
	}

	/**
	 * Write a text to the output
	 * 
	 * @param text
	 *            The text that should be written to the output.
	 */
	public static void writeString(String text) {
		System.out.println(text);
	}
}
