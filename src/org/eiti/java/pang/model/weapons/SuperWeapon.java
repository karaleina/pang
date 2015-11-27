package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.loaders.ImageLoader;
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
	
	public SuperWeapon(Point position) {
		super(
			ExtraObjectType.superWeapon,
			new Rectangle(
				position,
				getWidth(),
				getHeight()));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.superWeaponImage,
			shape.getPosition().x,
			shape.getPosition().y,
			getWidth(),
			getHeight(),
			null);
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new SuperMissile(
			calculateMissilePosition(
				shooter,
				SuperMissile.getWidth(),
				SuperMissile.getHeight()));
	}

}
