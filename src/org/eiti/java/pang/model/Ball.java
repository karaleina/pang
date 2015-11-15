package org.eiti.java.pang.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.shapes.Sphere;

public class Ball extends GameObject {

	private int level;
	
	public Ball(Point position, int level) {
		super(new Sphere(
				position,
				getRadius(level)));
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	@Override
	public void draw(Graphics g) {
		int radius = getRadius(level);
		g.setColor(Color.BLUE);
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
