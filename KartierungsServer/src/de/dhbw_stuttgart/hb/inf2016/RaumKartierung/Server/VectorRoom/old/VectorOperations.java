package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old;

import java.util.ArrayList;

/**
 * Created by samue on 20.06.2017.
 */
public class VectorOperations {
    public Vector add(Vector A, Vector B){
        return new Vector(A.getX() + B.getX(), A.getY() + B.getY());
    }

    public Vector sub(Vector A, Vector B){
        return new Vector(A.getX() - B.getX(), A.getY() - B.getY());
    }

    public double Scalarproduct(Vector A, Vector B){
        return ((A.getX() * B.getX()) + (A.getY() * B.getY()));
    }
    public double Length(Vector A){
        return Math.sqrt(Math.pow(A.getX(), 2) + Math.pow(A.getY(), 2));
    }
    public double AngleTwoVec(Vector A, Vector B){
        return Math.toDegrees(Math.acos(Scalarproduct(A,B)/(Length(A) * Length(B))));
    }
    public double Length(Vector A, Vector B){
        return Math.abs(Length(sub(A,B)));
    }

    public double Angle(Vector A){
        return Math.toDegrees(Math.asin(A.getX() / Length(A)));
    }

    public boolean checkSpace(Vector A, Vector B, Vector C, Vector D, ArrayList<Vector> Vectors) throws Exception {
        Line LowerLine;
        Line UpperLine;
        Line RightLine;
        Line LeftLine;
        if(A.getY() <= D.getY() && B.getY() <= C.getY()){
            LowerLine = new Line(A,B);
            UpperLine = new Line(D, C);
        }
        else if(A.getY() >= D.getY() && B.getY() >= C.getY()){
            LowerLine = new Line(D, C);
            UpperLine = new Line(A, B);
        }
        else{
            throw new Exception();
        }
        if(A.getX() <= B.getX() && D.getX() <= C.getX()){
            LeftLine = new Line(A, D);
            RightLine = new Line(B, C);
        }
        else if(A.getX() >= B.getX() && D.getX() >= C.getX()){
            LeftLine = new Line(B, C);
            RightLine = new Line(A, D);
        }
        else{
            throw new Exception();
        }

        for (Vector V : Vectors) {
            if(V.getX() > LeftLine.getVectorAtY(V.getY()).getX() && V.getX() < RightLine.getVectorAtY(V.getY()).getX() && V.getY() > LowerLine.getVectorAtX(V.getX()).getY() && V.getY() < UpperLine.getVectorAtX(V.getX()).getY()){
                return true;
            }
        }
        return false;
    }

    public Vector VectorOutOfAngleAndLength(double Angle, double Length){
        return new Vector( Length * Math.cos(Math.toRadians(Angle)), Length * Math.sin(Math.toRadians(Angle)));
    }
}
