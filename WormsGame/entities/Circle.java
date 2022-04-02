package entities;

import java.awt.Rectangle;

public class Circle {
	
	private int x;
	private int y;
	private double radius;

	public Circle(int x, int y, double radius){
		this.x=x;
		this.y=y;
		this.radius=radius;
	}

	public boolean intersects(Rectangle rectangle) {
		// TODO Auto-generated method stub
		return false;
	}
}
