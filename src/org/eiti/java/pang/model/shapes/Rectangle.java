package org.eiti.java.pang.model.shapes;

import java.awt.*;
import java.awt.geom.Point2D;

import org.eiti.java.pang.model.Collidable;

public class Rectangle extends Shape {
	
	private Dimension dimension;

	public Rectangle(Point2D position, Dimension dim) {
		super(position);
		this.dimension = dim;
	}
	
	public int getWidth()  {return (int) dimension.getWidth(); }
	public int getHeight() {return (int) dimension.getHeight();}

	@Override
	public boolean collidesWith(Collidable c) {
		// this trick allows to choose proper method without explicit type checking
		return c.collidesWith(this);
	}

	@Override
	public boolean collidesWith(Rectangle r) {
		return intervalIntersection(
					getExactX(), getExactX() + getWidth(),
					r.getExactX(), r.getExactX() + r.getWidth()) &&
				intervalIntersection(
					getExactY(), getExactY() + getHeight(),
					r.getExactY(), r.getExactY() + r.getHeight());
	}

	@Override
	public boolean collidesWith(Sphere s) {
		Point2D topLeftCorner = getPosition();
		Point2D topRightCorner = new Point2D.Double(getExactX() + getWidth(), getExactY());
		Point2D bottomLeftCorner = new Point2D.Double(getExactX(), getExactY() + getHeight());
		Point2D bottomRightCorner = new Point2D.Double(getExactX() + getWidth(), getExactY() + getHeight());
		
		return sphereCenterInRectangle(s) ||
			sphereCollidesWithLineSegment(s, topLeftCorner, topRightCorner) ||
			sphereCollidesWithLineSegment(s, topLeftCorner, bottomLeftCorner) ||
			sphereCollidesWithLineSegment(s, topRightCorner, bottomRightCorner) ||
			sphereCollidesWithLineSegment(s, bottomLeftCorner, bottomRightCorner);
	}

	private boolean sphereCenterInRectangle(Sphere s) {
		double minX = getExactX();
		double maxX = getExactX() + getWidth();
		double minY = getExactY();
		double maxY = getExactY() + getHeight();
		
		Point2D sphereCenter = s.getCenter();
		return sphereCenter.getX() >= minX && sphereCenter.getX() <= maxX &&
				sphereCenter.getY() >= minY && sphereCenter.getY() <= maxY;
	}
	
	private boolean sphereCollidesWithLineSegment(Sphere s, Point2D lineSegStart, Point2D lineSegEnd) {
		// find a line through given points and find line's intersection with the sphere
		Line lineThroughSegment = new Line(lineSegStart, lineSegEnd);
		Point2D[] lineIntersectionWithSphere = lineThroughSegment.intersectionWithSphere(s);
		if(lineIntersectionWithSphere == null) {
			return false;
		} else {
			// check if the line-sphere intersection is within given line segment
			boolean xProjectedIntersectionNotEmpty = intervalIntersection(
					lineSegStart.getX(), lineSegEnd.getX(),
					lineIntersectionWithSphere[0].getX(), lineIntersectionWithSphere[1].getX());
			boolean yProjectedIntersectionNotEmpty = intervalIntersection(
					lineSegStart.getY(), lineSegEnd.getY(),
					lineIntersectionWithSphere[0].getY(), lineIntersectionWithSphere[1].getY());
			return xProjectedIntersectionNotEmpty && yProjectedIntersectionNotEmpty;
		}
	}

	/**
	 * Check if intervals [a, b] and [c, d] intersect.
	 */
	private boolean intervalIntersection(double rawA, double rawB, double rawC, double rawD) {
		double a = Math.min(rawA, rawB);
		double b = Math.max(rawA, rawB);
		double c = Math.min(rawC, rawD);
		double d = Math.max(rawC, rawD);
		return (a <= c && c <= b) || (c <= a && a <= d);
	}
}
