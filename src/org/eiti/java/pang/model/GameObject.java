package org.eiti.java.pang.model;

import org.eiti.java.pang.model.shapes.Shape;

import java.awt.*;

public abstract class GameObject implements Drawable {
	
	protected Shape shape;
	
	protected GameObject(Shape shape) {
		this.shape = shape;
	}

	public void move(){}

	
	public boolean collidesWith(GameObject o) {
		return shape.collidesWith(o.shape);
	}

}
