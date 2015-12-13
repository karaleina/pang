package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class StandardWeapon extends Weapon {
	
	public StandardWeapon(Point position, Dimension gameWorldSize) {
		super(
			ExtraObjectType.standardWeapon,
			new Rectangle(position, 0, 0),
			gameWorldSize);
	}

	@Override
	public void draw(Graphics g) {
		throw new RuntimeException("Standard weapon should not be drawn!");
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new StandardMissile(
			calculateMissilePosition(
				shooter,
				StandardMissile.WIDTH,
				StandardMissile.HEIGHT),
			gameWorldSize);
	}

}
