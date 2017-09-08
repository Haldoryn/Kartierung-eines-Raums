package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;
/**
 * This class can calculate with double arrays.
 * @author Samuel Volz
 *
 */
public class VectorOperations {
	/**
	 * adds two vectors.
	 * @param Vector A is a double array.
	 * @param vector B is also a double array.
	 * @return a new double array. Its values are the added values of vactor A and B.
	 */
    public double[] add(double[] A, double[] B){
        return new double[]{A[0] + B[0], A[1] + B[1]};
    }
}
