package org.eiti.java.pang.model.weapons;

import java.awt.Point;

import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.shapes.Shape;

public abstract class Weapon extends ExtraObject {

	protected Weapon(Shape shape) {
		super(shape);
	}
	
	@Override
	public void interactWith(PlayerAvatar player) {
		player.setWeapon(this);
	}
	
	public abstract Missile shoot(PlayerAvatar shooter);
	
	protected Point calculateMissilePosition(
			PlayerAvatar shooter, int missileWidth, int missileHeight) {
		
		Rectangle shooterRectangle = shooter.getRectangularShape();
		int missileX = (int) shooterRectangle.getPosition().getX() +
				shooterRectangle.getWidth() / 2 -
				missileWidth / 2;
		int missileY = (int) shooterRectangle.getPosition().getY() - missileHeight;
		
		return new Point(missileX, missileY);
	}

}
