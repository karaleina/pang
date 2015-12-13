package org.eiti.java.pang.model;

import java.awt.Dimension;

import org.eiti.java.pang.model.shapes.Shape;

public abstract class GameObject implements Drawable {
	
	protected Shape shape;
	
	protected Dimension gameWorldSize;
	
	protected GameObject(Shape shape, Dimension gameWorldSize) {
		this.shape = shape;
		this.gameWorldSize = gameWorldSize;
	}

	/**
	 * A game object can become moveable by overriding
	 * this method. Method move(dt) should change object's
	 * location after given time interval.
	 * 
	 * @param dt time interval (milliseconds)
	 */
	public void move(double dt) {}

	
	public boolean collidesWith(GameObject o) {
		return shape.collidesWith(o.shape);
	}

}
