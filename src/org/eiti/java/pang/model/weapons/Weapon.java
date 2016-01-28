package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.shapes.Shape;

/**
 * This class represents a weapon.
 * @author Karolina
 *
 */
public abstract class Weapon extends ExtraObject {

	/**
	 * Constructs a Weapon
	 * @param type Type of the weapon
	 * @param shape Shape of the weapon
	 * @param gameWorldSize Abstract game world size
	 */
	protected Weapon(ExtraObjectType type, Shape shape, Dimension gameWorldSize) {
		super(type, shape, gameWorldSize);
	}
	
	/**
	 * Sets the weapon to the player.
	 */
	@Override
	public void interactWithPlayerAvatar() {
		PlayerAvatar.getInstance().setWeapon(this);
	}
	
	/**
	 * Enables to shoot as a player
	 * @return New missile
	 */
	public abstract Missile shoot();
	
	/**
	 * Calculates initial position of the missile
	 * @param missileWidth Missile's Width
	 * @param missileHeight Missile's Height
	 * @return Position of the missile
	 */
	protected Point2D calculateMissilePosition( int missileWidth, int missileHeight) {
		
		// these offsets allow the bullet to appear in front of gun barrel
		final int offsetX = -10;
		final int offsetY = 17;
		
		Rectangle shooterRectangle = PlayerAvatar.getInstance().getRectangularShape();
		int missileX = (int) shooterRectangle.getPosition().getX() +
				shooterRectangle.getWidth() / 2 -
				missileWidth / 2 + offsetX;
		int missileY = (int) shooterRectangle.getPosition().getY() -
				missileHeight + offsetY;
		
		return new Point2D.Double(missileX, missileY);
	}

}
