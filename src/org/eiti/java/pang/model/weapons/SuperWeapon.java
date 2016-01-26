package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

/**
 * This class represents super weapon, which
 * enables to shoot with super missiles.
 * @author Karolina
 *
 */
public class SuperWeapon extends Weapon {

	/**
	 * Super-weapon's width.  
	 */
	private int width;
	/**
	 * Super-weapon's height.
	 */
	private int height;
	
	/**
	 * Constructs the super-weapon object.
	 * @param position Initial position of the weapon
	 * @param width Super-weapon's width 
	 * @param height Super-weapon's height
	 * @param gameWorldSize Abstract game world size
	 */
	public SuperWeapon(Point2D position, int width, int height, Dimension gameWorldSize) {
		super(
			ExtraObjectType.superWeapon,
			new Rectangle(
				position,
				new Dimension(width, height)),
			gameWorldSize);
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Draws the super-weapon. 
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.getInstance().superWeaponImage,
			shape.getIntX(),
			shape.getIntY(),
			width,
			height,
			null);
	}

	/**
	 * Enables to shoot with super-weapon.
	 */
	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new SuperMissile(
			calculateMissilePosition(
						shooter,
						ImageLoader.getInstance().superMissileWidth,
						ImageLoader.getInstance().superMissileHeight),
				ImageLoader.getInstance().superMissileWidth,
				ImageLoader.getInstance().superMissileHeight,
			gameWorldSize);
	}

}
