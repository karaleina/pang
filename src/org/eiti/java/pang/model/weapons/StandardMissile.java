package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.globalConstants.ImageLoader;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.shapes.Rectangle;

public class StandardMissile extends Missile {
	
	public static int getWidth() {
		return 7;
	}
	
	public static int getHeight() {
		return 20;
	}

	public StandardMissile(Point position) {
		super(new Rectangle(position, getWidth(), getHeight()));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.standardMissileImage,
			shape.getPosition().x,
			shape.getPosition().y,
			getWidth(),
			getHeight(),
			null);
	}

	@Override
	public CollisionOutcome collisionOutcome(Ball b) {
		return CollisionOutcome.SPLIT;
	}

}
