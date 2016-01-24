package org.eiti.java.pang.model.shapes;

import java.awt.geom.Point2D;

/**
 * Line is represented as one of equations:
 * y = a * x + b 	(non-vertical lines)
 * 0 = x + b 		(vertical lines)
 *
 */
public class Line {
	
	private double a, b;

	private boolean vertical;
	private boolean horizontal;
	
	private final static double EPSILON = 1e-8;
	
	/**
	 * Construct a line from two points
	 */
	public Line(Point2D begin, Point2D end) {
		if(Math.abs(begin.getX() - end.getX()) < EPSILON) {
			vertical = true;
			a = 1;
			b = -begin.getX();
		} else {
			vertical = false;
			a = (end.getY() - begin.getY()) / (end.getX() - begin.getX());
			b = begin.getY() - a * begin.getX();
		}
		if(Math.abs(begin.getY() - end.getY()) < EPSILON) {
			horizontal = true;
			a = 0.0;
			b = begin.getY();
		}
	}
	
	/**
	 * Construct a line from two points and set helping variables
	 * that indicate whether the line is vertical, horizontal or diagonal.
	 */
	private Line(double a, double b, boolean vertical, boolean horizontal) {
		this.a = a;
		this.b = b;
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	
	/**
	 * Return a line orthogonal to this line.
	 * @param pointOnOrthogonal Any point that lies on the orthogonal line
	 */
	public Line getOrthogonalLine(Point2D pointOnOrthogonal) {
		if(horizontal) {
			return new Line(1.0, -pointOnOrthogonal.getX(), true, false);
		} else if(vertical) {
			return new Line(0.0, pointOnOrthogonal.getY(), false, true);
		} else {
			double newA = -a;
			double newB = pointOnOrthogonal.getY() - newA * pointOnOrthogonal.getX();
			return new Line(newA, newB, false, false);
		}
	}
	
	/**
	 * This returns either a point (when there is exactly one point of intersection)
	 * or null (when there are none or infinitely many points of intersection).
	 * @param line
	 * @return
	 */
	public Point2D intersectionWithLine(Line line) {
		
		if((horizontal && line.horizontal) ||
				(vertical && line.vertical) ||
				(Math.abs(a - line.a) < EPSILON)) {
			return null;
		} else if(vertical) {
			return intersectionPointForVertical(line);
		} else {
			return intersectionPointForNormal(line);
		}
	}
	
	/**
	 * Return intersection point between this line and given line,
	 * if this line is vertical.
	 */
	private Point2D intersectionPointForVertical(Line line) {
		assert(vertical && !line.vertical);
		double xCoord = -b;
		double yCoord = line.a * xCoord + line.b;
		return new Point2D.Double(xCoord, yCoord);
	}

	/**
	 * Return intersection point between this line and given line,
	 * if this line is not vertical.
	 */
	private Point2D intersectionPointForNormal(Line line) {
		assert(!vertical);
		if(line.vertical) {
			return line.intersectionPointForVertical(this);
		} else {
			double xCoord = (line.b - b) / (a - line.a);
			double yCoord = a * xCoord + b;
			return new Point2D.Double(xCoord, yCoord);
		}
	}
	
	/**
	 * This returns either a pair of points (when there is one or more points
	 * of intersection) or null (when line does not intersect with sphere).
	 */
	public Point2D[] intersectionWithSphere(Sphere s) {
		if(vertical) {
			return verticalLineIntersectionWithSphere(s);
		} else {
			return normalLineIntersectionWithSphere(s);
		}
	}
	
	/**
	 * Return 0, 1 or 2 points of intersection between
	 * this (vertical) line and given 2-dimensional sphere.
	 */
	private Point2D[] verticalLineIntersectionWithSphere(Sphere s) {
		assert(vertical);
		Point2D sphereCenter = s.getCenter();
		double x0 = sphereCenter.getX();
		double y0 = sphereCenter.getY();
		double r = s.getRadius();
		// these are coefficients of quadratic equation
		// for intersection of vertical line and sphere
		double equationA = 1;
		double equationB = 2 * y0;
		double equationC = y0 * y0 - r * r + Math.pow(-b - x0, 2);
		// find points of intersection by solving the equation
		double[] solutionForY = solveQuadraticEquation(equationA, equationB, equationC);
		if(solutionForY == null) {
			return null;
		} else {
			return new Point2D[] {
				new Point2D.Double(-b, solutionForY[0]),
				new Point2D.Double(-b, solutionForY[1])
			};
		}
	}

	/**
	 * Return 0, 1 or 2 points of intersection between
	 * this (non-vertical) line and given 2-dimensional sphere.
	 */
	private Point2D[] normalLineIntersectionWithSphere(Sphere s) {
		assert(!vertical);
		Point2D sphereCenter = s.getCenter();
		double x0 = sphereCenter.getX();
		double y0 = sphereCenter.getY();
		double r = s.getRadius();
		// these are coefficients of quadratic equation
		// for intersection of line and sphere
		double equationA = a * a + 1;
		double equationB = - 2 * x0 + 2 * a * (b - y0);
		double equationC = Math.pow(b - y0, 2) + x0 * x0 - r * r;
		// find points of intersection by solving the equation
		double[] solutionForX = solveQuadraticEquation(equationA, equationB, equationC);
		if(solutionForX == null) {
			return null;
		} else {
			return new Point2D[] {
				new Point2D.Double(solutionForX[0], a * solutionForX[0] + b),
				new Point2D.Double(solutionForX[1], a * solutionForX[1] + b)
			};
		}
	}
	
	/**
	 * Solve a * x^2 + b * x + c = 0
	 * @return 0 or 2 results
	 */
	private double[] solveQuadraticEquation(double a, double b, double c) {
		double delta = b * b - 4 * a * c;
		if(delta < 0) {
			return null;
		} else {
			double x1 = (-b + Math.sqrt(delta)) / (2 * a);
			double x2 = (-b - Math.sqrt(delta)) / (2 * a);
			double smallerX = Math.min(x1, x2);
			double biggerX = Math.max(x1, x2);
			return new double[] { smallerX, biggerX };
		}
	}
	
}
