package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

/**
 * Created by samue on 30.05.2017.
 */
public class Vector {
    private double x;
    private double y;

    public Vector(double x,double y){
        this.x = x;
        this.y = y;
    }

    public Vector() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector add(Vector Vec){
        this.x += Vec.getX();
        this.y += Vec.getY();
        return this;
    }
    public Vector clone(){
        return new Vector(x,y);
    }
}
