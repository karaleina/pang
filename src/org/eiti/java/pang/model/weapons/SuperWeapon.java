package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperWeapon extends Weapon {

	
	public SuperWeapon(Point position) {
		super(new Rectangle(
				position,
				ImageLoader.superWeaponImage.getWidth(),
				ImageLoader.superWeaponImage.getHeight()));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(ImageLoader.superWeaponImage, shape.getPosition().x, shape.getPosition().y, null);
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new SuperMissile(
			calculateMissilePosition(
				shooter,
				ImageLoader.superMissileImage.getWidth(),
				ImageLoader.superMissileImage.getHeight()));
	}

}
