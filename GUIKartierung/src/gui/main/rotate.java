package gui.main;

public class rotate {

	/**
	 * Calculates the degree the motors have to move
	 * to let the robot rotate in a specific angle
	 * @param degreeRobo the degree the robot should to rotate
	 */
	public void rotateDegrees(double degreeRobo)
	{
		double scopeWheels = Math.PI * 5.5;
		double scopeRobo = Math.PI * 15.8;
		double countWheelRotations = 0;
		double degreeWheelRotations = 0;
		double rotationDistanceRobo = 0;
		if(degreeRobo <= 180)
		{
			rotationDistanceRobo = scopeRobo * (degreeRobo / 360);
			countWheelRotations = rotationDistanceRobo / scopeWheels;
			degreeWheelRotations = countWheelRotations * 360;
			System.out.println("Um den Roboter um  " + degreeRobo + " Grad zu drehen müssen sich die Räder um so viel Grad drehen:");
			System.out.println(degreeWheelRotations);
			System.out.println("Rechtes Rad nach hinten und linkes Rad nach vorne");
		}
		else
		{
			rotationDistanceRobo = scopeRobo * ((degreeRobo - 180 ) / 360);
			countWheelRotations = rotationDistanceRobo / scopeWheels;
			degreeWheelRotations = countWheelRotations * 360;
			System.out.println("Um den Roboter um  " + degreeRobo + " Grad zu drehen müssen sich die Räder um so viel Grad drehen:");
			System.out.println(degreeWheelRotations);
			System.out.println("Rechtes Rad nach vorne und linkes Rad nach hinten");
		}
	}	
}
