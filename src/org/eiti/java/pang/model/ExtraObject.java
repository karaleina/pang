package org.eiti.java.pang.model;

import java.awt.Dimension;

import org.eiti.java.pang.model.shapes.Shape;

/**
 * ExtraObject is an object that appear during run of the game and can be collected by the player.
 */
public abstract class ExtraObject extends GameObject {
	
	private ExtraObjectType type;
	
	protected ExtraObject(ExtraObjectType type, Shape shape, Dimension gameWorldSize) {
		super(shape, gameWorldSize);
		this.type = type;
	}
	/**
	 * Heart increments lives number of the player.
	 * Weapon sets the weapon to the player.
	 */
	public abstract void interactWithPlayerAvatar();

}
