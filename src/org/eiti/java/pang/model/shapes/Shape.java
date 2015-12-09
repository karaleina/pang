package org.eiti.java.pang.model.shapes;

import java.awt.Point;

import org.eiti.java.pang.model.Collidable;

public abstract class Shape implements Collidable {
	
	Point position;					//położenie w intach raczej nie potrzebna, ale zachowaem by zmieniać jak najmniej
	private double exactX;			//położenie w doublach
	private double exactY;
	
	protected Shape(Point startPosition) {
		position = startPosition;
		exactX = startPosition.getX();
		exactY = startPosition.getY();
	}

	public double getExactX() { return exactX;}
	public double getExactY() { return exactY;}
	public Point getPosition() {return position;}

	public void moveTo(double newExactX, double newExactY){
		exactX = newExactX;
		exactY = newExactY;
		position.setLocation(newExactX, newExactY);
	}
}
