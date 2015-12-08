package org.eiti.java.pang.model;

import java.awt.*;

import org.eiti.java.pang.globalConstants.GlobalConfigLoader;
import org.eiti.java.pang.model.shapes.Sphere;

public class Ball extends GameObject {

	private int ballLevel;
	private double[] speedVector;
	double acceleration;		//gravitational acceleration (positive or negative)
	private Dimension gameWorldSize; //please do _not_ call a GameLevel method
	
	public Ball(
			Point position,
			int ballLevel,
			double[] initialSpeedVector,
			Dimension gameWorldSize) {

		super(new Sphere(
				position,
				getRadius(ballLevel)));

		this.ballLevel = ballLevel;
		this.speedVector = initialSpeedVector;
		this.gameWorldSize = gameWorldSize;
		acceleration = GlobalConfigLoader.gravity;
	}

	public void move() {
		//TODO
	};
	
	public int getBallLevel() {
		return ballLevel;
	}

	@Override
	public void draw(Graphics g) {
		int radius = getRadius(ballLevel);
		g.setColor(new Color(0xaa8030));
		g.fillOval(
			shape.getPosition().x,
			shape.getPosition().y,
			radius * 2,
			radius * 2);
	}
	
	private static int getRadius(int ballLevel) {
		return 10 + ballLevel * 3;
	}

}
