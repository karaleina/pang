package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.globalConstants.ImageLoader;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperMissile extends Missile {

	public final static int WIDTH = 7;
	public final static int HEIGHT = 30;
	
	public SuperMissile(Point2D position, Dimension gameWorldSize) {
		super(
			new Rectangle(
				position,
				WIDTH,
				HEIGHT),
			gameWorldSize);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.superMissileImage,
			shape.getIntX(),
			shape.getIntY(),
			getWidth(),
			getHeight(),
			null);
	}

	@Override
	public CollisionOutcome collisionOutcome(GameObject b) {
		return CollisionOutcome.DESTROY;
	}
	
	@Override
	public double getVelocity() {
		return 0.15;
	}
	
	@Override
	protected int getWidth() {
		return 7;
	}
	
	@Override
	protected int getHeight() {
		return 30;
	}

}
