package org.eiti.java.pang.model;

import org.eiti.java.pang.model.shapes.Shape;

public abstract class GameObject implements Drawable {
	
	protected Shape shape;
	
	protected GameObject(Shape shape) {
		this.shape = shape;
	}

	public void moveBy(int dx, int dy) {
		shape.moveBy(dx, dy);
	}
	
	public boolean collidesWith(GameObject o) {
		return shape.collidesWith(o.shape);
	}

}
