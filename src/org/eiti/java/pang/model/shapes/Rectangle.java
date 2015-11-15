package org.eiti.java.pang.model.shapes;

import java.awt.Point;

import org.eiti.java.pang.model.Collidable;

public class Rectangle extends Shape {
	
	private int width;
	private int height;

	public Rectangle(Point position, int width, int height) {
		super(position);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public boolean collidesWith(Collidable c) {
		// TODO Auto-generated method stub
		return false;
	}

}
