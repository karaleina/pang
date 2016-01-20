package org.eiti.java.pang.model.shapes;

import java.awt.geom.Point2D;

import org.eiti.java.pang.model.Collidable;

public abstract class Shape implements Collidable {
	
	private Point2D position;
	
	protected Shape(Point2D startPosition) {
		position = startPosition;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public double getExactX() {
		return position.getX();
	}
	
	public double getExactY() {
		return position.getY();
	}

	public void setExactLocation(double X, double Y) {
		position.setLocation(X,Y);
	}
	
	public int getIntX() {
		return (int) Math.round(position.getX());
	}
	
	public int getIntY() {
		return (int) Math.round(position.getY());
	}

	public void moveTo(double newExactX, double newExactY) {
		position.setLocation(newExactX, newExactY);
	}
}
