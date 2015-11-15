package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;

public class StandardMissile extends Missile {
	
	public final static int STANDARD_MISSILE_WIDTH = 5;
	public final static int STANDARD_MISSILE_HEIGHT = 20;

	public StandardMissile(Point position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public CollisionOutcome collisionOutcome(Ball b) {
		return CollisionOutcome.SPLIT;
	}

}
