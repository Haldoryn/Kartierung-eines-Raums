package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * Created by samue on 20.06.2017.
 */
public class VectorOperations {
    public Vector add(Vector A, Vector B){
        return new Vector(A.getX() + B.getX(), A.getY() + B.getY());
    }
}
