package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class StandardWeapon extends Weapon {

	private final static int STANDARD_WEAPON_WIDTH = 40;
	private final static int STANDARD_WEAPON_HEIGHT = 40;
	
	public StandardWeapon(Point position) {
		super(new Rectangle(
			position,
			STANDARD_WEAPON_WIDTH,
			STANDARD_WEAPON_HEIGHT));
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new StandardMissile(
			calculateMissilePosition(
				shooter,
				StandardMissile.STANDARD_MISSILE_WIDTH,
				StandardMissile.STANDARD_MISSILE_HEIGHT));
	}

}
