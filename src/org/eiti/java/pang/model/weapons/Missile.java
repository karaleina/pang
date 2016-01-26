package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import org.eiti.java.pang.game.events.MissileWindowExitListener;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Shape;

/**
 * This class represents missile
 * which main role is to destroy balls in game
 * @author Karolina
 *
 */
public abstract class Missile extends GameObject {
	
	/**
	 * This is hash set of listeners on the events
	 * when the missile is outside the game window. 
	 */
	private Set<MissileWindowExitListener> missileWindowExitListeners = new HashSet<>();
	
	/**
	 * Constructs the missile
	 * @param shape Missile's shape
	 * @param gameWorldSize Abstract Game Size 
	 */
	public Missile(Shape shape, Dimension gameWorldSize) {
		super(shape, gameWorldSize);
	}
	
	/**
	 * This method enables to move the missile
	 */
	@Override
	public void move(double dt) {
		double newY = shape.getExactY() - getVelocity() * dt;
		if(newY < -getHeight()) {
			fireMissileWindowExitEvent();
		} else {
			shape.moveTo(shape.getExactX(), newY);
		}
	}
	
	/**
	 * This method enables to add new listeners 
	 * on event when missile exits the window   
	 */
	public void addMissileWindowExitListener(MissileWindowExitListener listener) {
		missileWindowExitListeners.add(listener);
	}
	
	/**
	 * This method enables to remove the listener   
	 */
	public void removeMissileWindowExitListener(MissileWindowExitListener listener) {
		missileWindowExitListeners.remove(listener);
	}
	
	/**
	 * This method fire listeners
	 */
	private void fireMissileWindowExitEvent() {
		for(MissileWindowExitListener listener : missileWindowExitListeners) {
			listener.onMissileWindowExit(this);
		}
	}
	
	/**
	 * Returns the width of the missile
	 */
	protected abstract int getWidth();
	
	/**
	 * Returns the height of the missile
	 */
	protected abstract int getHeight();
	
	/**
	 * Returns the velocity of the missile
	 */
	public abstract double getVelocity();
	
	/**
	 * This method tells what is done when collision outcome
	 * @param b Game object 
	 */
	public abstract CollisionOutcome collisionOutcome(GameObject b);
	
}
