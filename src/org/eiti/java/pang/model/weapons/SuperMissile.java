package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperMissile extends Missile {

	private int width;
	private int height;
	
	public SuperMissile(Point2D position, int width, int height, Dimension gameWorldSize) {
		super(
			new Rectangle(
				position,
				new Dimension(width,
				height)),
			gameWorldSize);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.getInstance().superMissileImage,
			shape.getIntX(),
			shape.getIntY(),
			width,
			height,
			null);
	}

	@Override
	public CollisionOutcome collisionOutcome(GameObject b) {
		return CollisionOutcome.DESTROY;
	}
	
	@Override
	public double getVelocity() {
		return 0.4;
	}
	
	@Override
	protected int getWidth() {
		return width;
	}
	
	@Override
	protected int getHeight() {
		return height;
	}

}
