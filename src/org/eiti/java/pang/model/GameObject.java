package org.eiti.java.pang.model;

import org.eiti.java.pang.model.shapes.Shape;

import java.awt.*;

public abstract class GameObject implements Drawable {
	
	protected Shape shape;
	
	protected GameObject(Shape shape) {
		this.shape = shape;
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
