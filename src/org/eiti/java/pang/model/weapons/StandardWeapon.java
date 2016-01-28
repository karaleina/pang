package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

/**
 * This class represents the standard weapon
 * given at the beginning of every level.
 * This weapon is invisible and enables to shoot.
 * @author Karolina
 *
 */
public class StandardWeapon extends Weapon {
	
	/**
	 * Constructs the standard weapon.
	 * @param position Position of standard weapon
	 * @param gameWorldSize Abstract game world size
	 */
	public StandardWeapon(Point2D position, Dimension gameWorldSize) {
		super(
			ExtraObjectType.standardWeapon,
			new Rectangle(position, new Dimension(0, 0)	),
				//StandardWeapon is not visible, so we can give any number
			gameWorldSize);
	}
	/**
	 * Draws the standard weapon; 
	 * This method should't be used,
	 * because weapon is invisible.
	 */
	@Override
	public void draw(Graphics g) {
		throw new RuntimeException("Standard weapon should not be drawn!");
	}

	/**
	 * This method is responsible for shooting; 
	 * Returns new missile.
	 */
	@Override
	public Missile shoot() {
		return new StandardMissile(
			calculateMissilePosition(
				StandardMissile.WIDTH,
				StandardMissile.HEIGHT),
				gameWorldSize);
	}

}
