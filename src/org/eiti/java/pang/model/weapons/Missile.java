package org.eiti.java.pang.model.weapons;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Shape;

public abstract class Missile extends GameObject {
	
	public Missile(Shape shape) {
		super(shape);
	}
	
	public abstract CollisionOutcome collisionOutcome(Ball b);
	
}
