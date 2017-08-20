package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Protocol.Commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Parser class for the command of the robot text protocoll.
 * 
 * @author Julian Vogel
 *
 */
public class CommandParser {
	// Stores all supported command classes.
	private static Class<?>[] commandClasses = { GetGyroscopeCmd.class, GetStatusCmd.class, GetUltrasonicCmd.class,
			MoveMotorCmd.class, MoveSensorCmd.class, ResetCmd.class, ReturnGyroscopeCmd.class, ReturnMessageCmd.class,
			ReturnMotorCmd.class, ReturnResetCmd.class, ReturnSensorCmd.class, ReturnSensorCmd.class,
			ReturnStatusCmd.class, ReturnUltrasonicCmd.class };

	// Finds a fitting constructor for a command class.
	private static Constructor<?> GetConstructor(Class<?> cls) {
		for (Constructor<?> cons : cls.getDeclaredConstructors()) {
			if (cons.getParameterTypes().length > 0 && cons.getModifiers() == Modifier.PUBLIC) {
				return cons;
			}
		}
		// Find the default constructor if no fitting parameterized constructor
		// was found.

		try {
			return cls.getConstructor();
		} catch (Exception ex) {
			throw new IllegalArgumentException("The given class does not contain a default constructor.");
		}

	}

	// Reads the data types of the parameters of a constructor.
	private static DataType[] GetConstructorParameters(Constructor<?> cons) {
		ArrayList<DataType> list = new ArrayList<>();
		for (Class<?> dataType : cons.getParameterTypes()) {
			if (dataType.equals(Integer.class) || dataType.equals(int.class)) {
				list.add(DataType.Integer);
			} else if (dataType.equals(Double.class) || dataType.equals(double.class)) {
				list.add(DataType.Double);
			} else if (dataType.equals(String.class)) {
				list.add(DataType.String);
			} else {
				throw new IllegalArgumentException(
						"The given class contains a constructor with a unknown parameter datatype.");
			}
		}
		return (DataType[]) list.toArray(new DataType[list.size()]);
	}

	private static Object[] parseParameters(String[] cmdParts, DataType[] params) {
		List<Object> parsedValues = new ArrayList<>();

		// If the length of the cmd parts array is not correct the command can
		// not be parsed.
		if (cmdParts.length + 1 < params.length) {
			throw new IllegalArgumentException("The given command string does not contain enough parameter values.");
		}

		// Detect the type of the parameter and use the correct parser class.
		for (int i = 0; i < params.length; i++) {
			switch (params[i]) {
			case Double:
				parsedValues.add(Double.parseDouble(cmdParts[i + 1]));
				break;
			case Integer:
				parsedValues.add(Integer.parseInt(cmdParts[i + 1]));
				break;
			case String:
				parsedValues.add(cmdParts[i + 1]);
				break;
			default:
				break;// This case can not happen(it is checked earlier).
			}
		}

		return parsedValues.toArray();
	}

	// Stores the parameter data types of the constructors of the command
	// classes.
	private HashMap<String, DataType[]> constructionParameters = new HashMap<>();

	// Stores the constructors for instantiating the command classes.
	private HashMap<String, Constructor<?>> nameConstructorMap = new HashMap<>();

	/**
	 * Initializes a new instance of the {@link CommandParser} class.
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public CommandParser() throws InstantiationException, IllegalAccessException {
		// Initialize the data that is required to later parse the parameters
		// and initialize the command classes.
		for (Class<?> cls : commandClasses) {

			// Find the parameterized constructor or return the default
			// constructor.
			Constructor<?> foundConstructor = GetConstructor(cls);
			String name = ((CommandBase) cls.newInstance()).getType().toString();
			nameConstructorMap.put(name, foundConstructor);
			// Store the parameter types as enum values.
			constructionParameters.put(name, GetConstructorParameters(foundConstructor));
		}
	}

	/**
	 * Parses a command string and returns a {@link CommandBase} object.
	 * 
	 * @param command
	 *            The command string that should be parsed.
	 * @return The parsed {@link CommandBase} object.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public CommandBase Parse(String command)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Check the parameter
		if (command == null) {
			throw new IllegalArgumentException("The 'command' parameter must not be null.");
		}
		String[] cmdParts = command.split(";");
		if (cmdParts.length < 1) {
			throw new IllegalArgumentException("The 'command' parameter does not contain a valid command");
		}

		// Split the input String to get the identifier and parameters.
		String cmd = cmdParts[0];
		cmdParts = cmd.split(",");
		String identifier = cmdParts[0];

		DataType[] params=null;
		if(constructionParameters.containsKey(identifier))
		{
			params=constructionParameters.get(identifier);
		}
		else
		{
			throw new IllegalArgumentException("Invalid command identifier in 'command' parameter.");
		}

		// Parse the parameter value using the stored type enum values.
		Object[] parameterValues = parseParameters(cmdParts, params);

		// Instantiate the command with parsed values.
		return (CommandBase) nameConstructorMap.get(identifier).newInstance(parameterValues);
	}
}
