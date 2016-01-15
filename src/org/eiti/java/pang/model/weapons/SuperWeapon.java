package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperWeapon extends Weapon {

	private int width;
	private int height;

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
