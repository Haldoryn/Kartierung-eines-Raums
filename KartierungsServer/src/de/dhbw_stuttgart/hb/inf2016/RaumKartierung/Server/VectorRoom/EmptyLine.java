package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * Created by samue on 20.06.2017.
 */
public class EmptyLine {

    private Vector A;
    private Vector B;
    public EmptyLine(Vector A, Vector B){
        this.A = A;
        this.B = B;
    }
    public Vector getA() {
        return A;
    }

    public void setA(Vector a) {
        A = a;
    }

    public Vector getB() {
        return B;
    }

    public void setB(Vector b) {
        B = b;
    }

}
