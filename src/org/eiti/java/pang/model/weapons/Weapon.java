package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.shapes.Shape;

public abstract class Weapon extends ExtraObject {

	protected Weapon(ExtraObjectType type, Shape shape, Dimension gameWorldSize) {
		super(type, shape, gameWorldSize);
	}
	
	@Override
	public void interactWith(PlayerAvatar player) {
		player.setWeapon(this);
	}
	
	public abstract Missile shoot(PlayerAvatar shooter);
	
	protected Point2D calculateMissilePosition(
			PlayerAvatar shooter, int missileWidth, int missileHeight) {
		
		// these offsets allow the bullet to appear in front of gun barrel
		final int offsetX = -10;
		final int offsetY = 17;
		
		Rectangle shooterRectangle = shooter.getRectangularShape();
		int missileX = (int) shooterRectangle.getPosition().getX() +
				shooterRectangle.getWidth() / 2 -
				missileWidth / 2 + offsetX;
		int missileY = (int) shooterRectangle.getPosition().getY() -
				missileHeight + offsetY;
		
		return new Point2D.Double(missileX, missileY);
	}

}
