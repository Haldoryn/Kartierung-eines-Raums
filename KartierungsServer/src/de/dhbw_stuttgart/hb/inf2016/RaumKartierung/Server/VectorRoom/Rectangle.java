package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.VectorRoom;

public class Rectangle {
	private float x;
	private float y;
	private float width;
	private float heigth;
	public Rectangle(float x, float y, float width, float heigth) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getWidth() {
		return width;
	}
	public float getHeigth() {
		return heigth;
	}
}
