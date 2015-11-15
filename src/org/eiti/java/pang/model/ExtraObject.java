package org.eiti.java.pang.model;

import org.eiti.java.pang.model.shapes.Shape;

public abstract class ExtraObject extends GameObject {
	
	protected ExtraObject(Shape shape) {
		super(shape);
	}

	public abstract void interactWith(PlayerAvatar player);

}
