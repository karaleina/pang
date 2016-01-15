package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class StandardWeapon extends Weapon {
	
	public StandardWeapon(Point2D position, Dimension gameWorldSize) {
		super(
			ExtraObjectType.standardWeapon,
			new Rectangle(position, new Dimension(0, 0)	),
				//StandardWeapon is not visible, so we can give any number
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
