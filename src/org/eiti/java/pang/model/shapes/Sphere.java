package org.eiti.java.pang.model.shapes;

import java.awt.geom.Point2D;

import org.eiti.java.pang.model.Collidable;

public class Sphere extends Shape {
	
	private double radius;

	public Sphere(Point2D position, double radius) {
		super(position);
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Point2D getCenter() {
		Point2D topLeftCorner = getPosition();
		return new Point2D.Double(
			topLeftCorner.getX() + radius,
			topLeftCorner.getY() + radius);
	}

	@Override
	public boolean collidesWith(Collidable c) {
		// this trick allows to choose proper method without explicit type checking
		return c.collidesWith(this);
	}

	@Override
	public boolean collidesWith(Rectangle r) {
		// already implemented in Rectangle
		return r.collidesWith(this);
	}

	@Override
	public boolean collidesWith(Sphere s) {
		// Not implemented, because we don't need sphere-sphere collision detection
		throw new UnsupportedOperationException();
	}
}
