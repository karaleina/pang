package org.eiti.java.pang.model;

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
		// TODO Auto-generated method stub
	}
	
	private static int getRadius(int ballLevel) {
		return 10 + ballLevel * 3;
	}

}
