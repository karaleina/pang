package org.eiti.java.pang.model;

import java.awt.Dimension;

import org.eiti.java.pang.model.shapes.Shape;

public abstract class ExtraObject extends GameObject {
	
	private ExtraObjectType type;
	
	protected ExtraObject(ExtraObjectType type, Shape shape, Dimension gameWorldSize) {
		super(shape, gameWorldSize);
		this.type = type;
	}

	public abstract void interactWithPlayerAvatar();
	
	public ExtraObjectType getExtraObjectType() {
		return type;
	}

}
