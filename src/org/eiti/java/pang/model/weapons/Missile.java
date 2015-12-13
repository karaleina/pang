package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import org.eiti.java.pang.game.events.MissileWindowExitListener;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Shape;

public abstract class Missile extends GameObject {
	
	private Set<MissileWindowExitListener> missileWindowExitListeners = new HashSet<>();
	
	public Missile(Shape shape, Dimension gameWorldSize) {
		super(shape, gameWorldSize);
	}
	
	@Override
	public void move(double dt) {
		double newY = shape.getExactY() - getVelocity() * dt;
		if(newY < -getHeight()) {
			fireMissileWindowExitEvent();
		} else {
			shape.moveTo(shape.getExactX(), newY);
		}
	}
	
	public void addMissileWindowExitListener(MissileWindowExitListener listener) {
		missileWindowExitListeners.add(listener);
	}
	
	public void removeMissileWindowExitListener(MissileWindowExitListener listener) {
		missileWindowExitListeners.remove(listener);
	}
	
	private void fireMissileWindowExitEvent() {
		for(MissileWindowExitListener listener : missileWindowExitListeners) {
			listener.onMissileWindowExit(this);
		}
	}
	
	protected abstract int getWidth();
	
	protected abstract int getHeight();
	
	public abstract double getVelocity();
	
	public abstract CollisionOutcome collisionOutcome(GameObject b);
	
}
