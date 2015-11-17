package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperMissile extends Missile {
	
	

	public SuperMissile(Point position) {
		super(new Rectangle(position, ImageLoader.superMissileImage.getWidth(), ImageLoader.superMissileImage.getHeight()));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(ImageLoader.superMissileImage, shape.getPosition().x, shape.getPosition().y, null);
	}

	@Override
	public CollisionOutcome collisionOutcome(Ball b) {
		return CollisionOutcome.DESTROY;
	}

}
