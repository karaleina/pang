package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.StandardWeapon;
import org.eiti.java.pang.model.weapons.Weapon;

public class PlayerAvatar extends GameObject {
	
	private int lives;
	private Weapon weapon;
	
	private final static int PLAYER_AVATAR_WIDTH = 50;
	private final static int PLAYER_AVATAR_HEIGHT = 100;
	
	public PlayerAvatar(Point position, int initialLives) {
		super(new Rectangle(
				position,
				PLAYER_AVATAR_WIDTH,
				PLAYER_AVATAR_HEIGHT));
		this.lives = initialLives;
		this.weapon = new StandardWeapon(position);
	}

	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Rectangle getRectangularShape() {
		return (Rectangle) shape;
	}
	
	public Missile shoot() {
		return weapon.shoot(this);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

}
