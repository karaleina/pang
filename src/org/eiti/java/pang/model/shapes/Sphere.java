package org.eiti.java.pang.model.shapes;

import java.awt.Point;

import org.eiti.java.pang.model.Collidable;

public class Sphere extends Shape {


	public Sphere(Point position) {
		super(position);
	}


	@Override
	public boolean collidesWith(Collidable c) {
		// TODO Auto-generated method stub
		return false;
	}

}
