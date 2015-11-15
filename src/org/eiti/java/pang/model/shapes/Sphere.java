package org.eiti.java.pang.model.shapes;

import java.awt.Point;

import org.eiti.java.pang.model.Collidable;

public class Sphere extends Shape {
	
	private int radius;

	public Sphere(Point position, int radius) {
		super(position);
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}

	@Override
	public boolean collidesWith(Collidable c) {
		// TODO Auto-generated method stub
		return false;
	}

}
