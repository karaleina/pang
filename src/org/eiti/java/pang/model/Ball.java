package org.eiti.java.pang.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.shapes.Sphere;

public class Ball extends GameObject {

	private int ballLevel;
	
	public Ball(Point position, int ballLevel) {
		super(new Sphere(
				position,
				getRadius(ballLevel)));
		this.ballLevel = ballLevel;
	}
	
	public int getLevel() {
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
