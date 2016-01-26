package org.eiti.java.pang.model.shapes;

import java.awt.geom.Point2D;

import org.eiti.java.pang.model.Collidable;

/**
 * This class represents Shape.
 * @author Karolina
 *
 */
public abstract class Shape implements Collidable {
	
	/**
	 * This represents position of the Shape.
	 */
	private Point2D position;
	
	/**
	 * This method constructs the shape at the startPosition.
	 * @param startPosition Initial position of the Shape
	 */
	protected Shape(Point2D startPosition) {
		position = startPosition;
	}
	
	/**
	 * This is method which enables to get the position of the Shape.
	 */
	public Point2D getPosition() {
		return position;
	}
	
	/**
	 * This enables to get X position
	 */
	public double getExactX() {
		return position.getX();
	}
	
	/**
	 * This enables to get Y position
	 */
	public double getExactY() {
		return position.getY();
	}

	/**
	 * This method enables to change location of the Shape
	 * if it is necessary. 
	 * @param X Target X position (double)
	 * @param Y Target Y position (double)
	 */
	public void setExactLocation(double X, double Y) {
		position.setLocation(X,Y);
	}
	
	/**
	 * This method returns rounded to integer X position
	 */
	public int getIntX() {
		return (int) Math.round(position.getX());
	}
	
	/**
	 * This method returns rounded to integer Y position
	 */
	public int getIntY() {
		return (int) Math.round(position.getY());
	}

	/**
	 * This method enables to move the Shape to the exact position
	 * @param newExactX New exact X position (double)
	 * @param newExactY New exaxt Y position (double)
	 */
	public void moveTo(double newExactX, double newExactY) {
		position.setLocation(newExactX, newExactY);
	}
}
