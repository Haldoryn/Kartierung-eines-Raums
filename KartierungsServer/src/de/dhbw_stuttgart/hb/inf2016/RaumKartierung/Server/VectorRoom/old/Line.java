package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom.old;

import java.util.ArrayList;

/**
 * Created by samue on 20.06.2017.
 */
public class Line {
    VectorOperations VO = new VectorOperations();
    private Vector A;
    private Vector B;
    public Line(Vector A, Vector B){
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

    public ArrayList<Vector> getVectors(){
        ArrayList<Vector> vectors = new ArrayList<>();
        double Length = VO.Length(A,B);
        double Angle = VO.Angle(VO.sub(A,B));
        for(int i = 1; i<=9; i++){
            double Adjacent = Math.cos(Angle) * i;
            double Opposite = Math.sin(Angle) * i;
            vectors.add(VO.add(A, new Vector(Adjacent, Opposite)));
        }
        return vectors;
    }

    public Vector getVectorAtX(double X){
        if (A.getX() > B.getX()){
            if (X < A.getX() && X > B.getX()){
                double d = A.getY() - B.getY();
                double a = X - B.getX();
                double b = A.getX() - B.getX();
                Vector C = new Vector(a, (d*a)/b);
                return VO.add(B, C);
            }
        }else if (B.getX() > A.getX()){
            if(X < B.getX() && X > A.getX()){
                double d = B.getY() - A.getY();
                double a = X - A.getX();
                double b = B.getX() - A.getX();
                Vector C = new Vector(a, (d*a)/b);
                return VO.add(A, C);
            }
        }else if (A.getX() == B.getX()){
            if (X == A.getX()){
                return A.clone();
            }
        }
        return null;
    }
    public Vector getVectorAtY(double Y){
        if (A.getY() > B.getY()){
            if (Y < A.getY() && Y > B.getY()){
                double d = A.getX() - B.getX();
                double a = Y - B.getY();
                double b = A.getY() - B.getY();
                Vector C = new Vector((d*a)/b, a);
                return VO.add(B, C);
            }
        }else if (B.getY() > A.getY()){
            if(Y < B.getY() && Y > A.getY()){
                double d = B.getX() - A.getX();
                double a = Y - A.getY();
                double b = B.getY() - A.getY();
                Vector C = new Vector((d*a)/b, a);
                return VO.add(A, C);
            }
        }else if (A.getY() == B.getY()){
            if (Y == A.getY()){
                return A.clone();
            }
        }
        return null;
    }
}
