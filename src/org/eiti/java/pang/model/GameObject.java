package org.eiti.java.pang.model;

import java.awt.Dimension;

import org.eiti.java.pang.model.shapes.Shape;

/**
 * Every object that can move and collide with other objects should extend this class.
 * This class implements the Drawable interface.
 */
public abstract class GameObject implements Drawable {
	
	protected Shape shape;
	
	protected Dimension gameWorldSize;

	/**
	 *
	 * @param shape	Instance of a Line, Rectangle or Sphere.
	 * @param gameWorldSize Useful to check if object reach border of the world of game.
     */
	protected GameObject(Shape shape, Dimension gameWorldSize) {
		this.shape = shape;
		this.gameWorldSize = gameWorldSize;
	}

	/**
	 * A game object can become movable by overriding
	 * this method. Method move(dt) should change object's
	 * location after given time interval.
	 * 
	 * @param dt time interval (milliseconds)
	 */
	public void move(double dt) {}

	/**
	 *
	 * @param o other GameObject;
	 * @return True if collision had place, false if not.
     */
	public boolean collidesWith(GameObject o) {
		return shape.collidesWith(o.shape);
	}

}
