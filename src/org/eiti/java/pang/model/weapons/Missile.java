package org.eiti.java.pang.model.weapons;

import java.awt.Point;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Rectangle;

public abstract class Missile extends GameObject {

	private final static int WIDTH = 10;
	private final static int HEIGHT = 30;
	
	public Missile(Point position) {
		super(new Rectangle(
				position,
				WIDTH,
				HEIGHT));
		
	}
	
	public abstract CollisionOutcome collisionOutcome(Ball b);
	
}
