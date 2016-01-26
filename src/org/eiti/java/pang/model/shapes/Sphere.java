package org.eiti.java.pang.model.shapes;

import java.awt.geom.Point2D;

import org.eiti.java.pang.model.Collidable;

/**
 * This class represents a Sphere.
 * @author Karolina
 *
 */
public class Sphere extends Shape {
	
	/**
	 * Sphere radius (double).
	 */
	private double radius;

	/**
	 * Constructs a sphere from top left point  
	 * @param position Position of the sphere 
	 * @param radius Radius of the sphere
	 */
	public Sphere(Point2D position, double radius) {
		super(position);
		this.radius = radius;
	}
	
	/**
	 * Returns radius of the sphere.
	 * @return
	 */
	public double getRadius() {
		return radius;
	}
	/**
	 * Calculates and returns position of the center 
	 * of the sphere  
	 */
	public Point2D getCenter() {
		Point2D topLeftCorner = getPosition();
		return new Point2D.Double(
			topLeftCorner.getX() + radius,
			topLeftCorner.getY() + radius);
	}

	/**
	 * Check if this sphere collides with a collidable object
	 */
	@Override
	public boolean collidesWith(Collidable c) {
		// this trick allows to choose proper method without explicit type checking
		return c.collidesWith(this);
	}


	/**
	 * Check if this sphere collides with a rectangle
	 */
	@Override
	public boolean collidesWith(Rectangle r) {
		// already implemented in Rectangle
		return r.collidesWith(this);
	}

	/**
	 * Check if this sphere collides with a sphere
	 */
	@Override
	public boolean collidesWith(Sphere s) {
		// Not implemented, because we don't need sphere-sphere collision detection
		throw new UnsupportedOperationException();
	}
}
