package org.eiti.java.pang.model;

import org.eiti.java.pang.model.shapes.Shape;

public abstract class ExtraObject extends GameObject {
	
	private ExtraObjectType type;
	
	protected ExtraObject(ExtraObjectType type, Shape shape) {
		super(shape);
		this.type = type;
	}

	public abstract void interactWith(PlayerAvatar player);
	
	public ExtraObjectType getExtraObjectType() {
		return type;
	}

}
