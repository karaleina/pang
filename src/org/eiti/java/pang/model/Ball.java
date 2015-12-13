package org.eiti.java.pang.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;

import org.eiti.java.pang.globalConstants.GlobalConfigLoader;
import org.eiti.java.pang.model.shapes.Sphere;

public class Ball extends GameObject {

	private int ballLevel;
	private double[] speedVector;
	double acceleration;				//gravitational acceleration (positive or negative)
	
	public Ball(
			Point2D position,
			int ballLevel,
			double[] initialSpeedVector,
			Dimension gameWorldSize) {

		super(new Sphere(position, radiusForBallLevel(ballLevel)), gameWorldSize);

		this.ballLevel = ballLevel;
		this.gameWorldSize = gameWorldSize;
		speedVector = initialSpeedVector;
		acceleration = GlobalConfigLoader.gravity;
	}

	@Override
	public void move(double dt) {

		double oldX = this.shape.getExactX();
		double oldY = this.shape.getExactY();
		double newX = oldX + speedVector[0] * dt;
		double newY = oldY + speedVector[1] * dt + acceleration * dt * dt / 2;

		speedVector[1] += acceleration * dt; //vertical

		double minX = 0;
		double minY = 0;
		double maxX = gameWorldSize.getWidth() - getRadius() * 2;
		double maxY = gameWorldSize.getHeight() - getRadius() * 2;

		if (newX > minX && newX < maxX && newY > minY && newY < maxY){
			shape.moveTo(newX, newY);
		} else {
			//ball reach bound of permitted area
			//move direction separated to avoid mess in case when ball reach corner of a panel
			if (newX <= minX) {
				shape.moveTo(minX, oldY);
				speedVector[0] *= -1;		//odbicie od sciany
			} else if (newX >= maxX) {
				shape.moveTo(maxX, oldY);
				speedVector[0] *= -1;		//odbicie od sciany
			}
			if (newY <= minY) {
				shape.moveTo(oldX, minY);
				speedVector[1] *= -1;		//odbicie od podlogi
			} else if (newY >= maxY) {
				shape.moveTo(oldX, maxY);
				speedVector[1] *= -1;		//odbicie od sufitu
			}
		}
	}
	
	public Collection<Ball> split() {
		Point2D baseBallPosition = getPosition();
		double newBallsRadius = Ball.radiusForBallLevel(ballLevel - 1);
		// left ball goes to upper left
		Point2D leftBallPosition = new Point2D.Double(
			baseBallPosition.getX(),
			baseBallPosition.getY() + getRadius() - newBallsRadius);
		Ball leftBall = new Ball(
			leftBallPosition,
			ballLevel - 1,
			new double[] { -Math.abs(speedVector[0]), -Math.abs(speedVector[1]) },
			gameWorldSize);
		// right ball goes to upper right
		Point2D rightBallPosition = new Point2D.Double(
			baseBallPosition.getX() + 2 * getRadius() - 2 * newBallsRadius,
			leftBallPosition.getY());
		Ball rightBall = new Ball(
			rightBallPosition,
			ballLevel - 1,
			new double[] { Math.abs(speedVector[0]), -Math.abs(speedVector[1]) },
			gameWorldSize);
		return Arrays.asList(leftBall, rightBall);
	}
	
	public int getBallLevel() {
		return ballLevel;
	}
	
	public Point2D getPosition() {
		return shape.getPosition();
	}
	
	public double getRadius() {
		return ((Sphere) shape).getRadius();
	}

	@Override
	public void draw(Graphics g){
		g.setColor(new Color(0x2020aa));
		g.fillOval(
			shape.getIntX(),
			shape.getIntY(),
			(int) getRadius() * 2,
			(int) getRadius() * 2
		);
	}
	
	public static double radiusForBallLevel(int ballLevel) { 
		return 20 * ballLevel;
	}
}
