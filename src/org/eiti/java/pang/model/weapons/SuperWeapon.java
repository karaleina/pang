package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperWeapon extends Weapon {

	private final static int SUPER_WEAPON_WIDTH = 40;
	private final static int SUPER_WEAPON_HEIGHT = 40;
	
	public SuperWeapon(Point position) {
		super(new Rectangle(
				position,
				SUPER_WEAPON_WIDTH,
				SUPER_WEAPON_HEIGHT));
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new SuperMissile(
			calculateMissilePosition(
				shooter,
				SuperMissile.SUPER_MISSILE_WIDTH,
				SuperMissile.SUPER_MISSILE_HEIGHT));
	}

}
