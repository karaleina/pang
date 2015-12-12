package org.eiti.java.pang.model;

import java.awt.*;

import org.eiti.java.pang.globalConstants.GlobalConfigLoader;
import org.eiti.java.pang.model.shapes.Sphere;

public class Ball extends GameObject {

	private int ballLevel;
	private double[] speedVector;
	double acceleration;				//gravitational acceleration (positive or negative)
	private double radius;
	private Dimension gameWorldSize; 	//please do _not_ call a GameLevel method
	//double[] accesibleArea;			//[minX, maxX, minY, maxY]
	
	public Ball(
			Point position,
			int ballLevel,
			double[] initialSpeedVector,
			Dimension gameWorldSize) {

		super(new Sphere(position));

		this.ballLevel = ballLevel;
		speedVector = initialSpeedVector;
		this.gameWorldSize = gameWorldSize;
		acceleration = GlobalConfigLoader.gravity;

		setRadius(ballLevel);

	}

	@Override
	public void move() {
		double newX;
		double newY;
		double oldX;
		double oldY;

		oldX = this.shape.getExactX();
		oldY = this.shape.getExactY();
		newX = oldX + speedVector[0];
		newY = oldY + speedVector[1];

		speedVector[1] += acceleration; //vertical

		double minX = radius;
		double minY = radius;
		double maxX = gameWorldSize.getWidth()  - radius;
		double maxY = gameWorldSize.getHeight() - radius;

		if (newX > minX && newX < maxX && newY > minY && newY <maxY){
			shape.moveTo(newX, newY);
		} else {
			//ball reach bound of permitted area
			//move direction separated to avoid mess in case when ball reach corner of a panel
			if (newX <= minX) {
				shape.moveTo(minX, oldY);
				speedVector[0] *= -1;		//odbicie od sciany
			}
			else if (newX >= maxX) {
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
	
	public int getBallLevel() {
		return ballLevel;
	}

	@Override
	public void draw(Graphics g){
		g.setColor(new Color(0xaa8030));
		g.fillOval(
			shape.getPosition().x,
			shape.getPosition().y,
			(int)radius * 2,
			(int)radius * 2
		);
	}
	
	private void   setRadius(int ballLevel) { radius = 10 + ballLevel * 3; }
	private double getRadius() { return radius; }

}
