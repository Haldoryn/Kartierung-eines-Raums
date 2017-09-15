package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

/**
 * Defines the different command types. These are also the names of the types.
 * 
 * @author JVogel
 *
 */
public enum CommandType {
	moveMotor, moveSensor, getUltrasonic, getGyroscope, reset, getStatus, returnUltrasonic, returnGyroscope, returnMotor, returnSensor, returnReset, returnStatus, returnMessage
}
