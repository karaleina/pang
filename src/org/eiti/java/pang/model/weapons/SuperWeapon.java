package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.globalConstants.ImageLoader;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperWeapon extends Weapon {

	public static int getWidth() {
		return 90;
	}
	
	public static int getHeight() {
		return 60;
	}
	
	public SuperWeapon(Point2D position, Dimension gameWorldSize) {
		super(
			ExtraObjectType.superWeapon,
			new Rectangle(
				position,
				getWidth(),
				getHeight()),
			gameWorldSize);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.superWeaponImage,
			shape.getIntX(),
			shape.getIntY(),
			getWidth(),
			getHeight(),
			null);
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new SuperMissile(
			calculateMissilePosition(
				shooter,
				SuperMissile.WIDTH,
				SuperMissile.HEIGHT),
			gameWorldSize);
	}

}
