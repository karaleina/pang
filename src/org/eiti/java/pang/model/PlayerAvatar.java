package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.StandardWeapon;
import org.eiti.java.pang.model.weapons.Weapon;

public class PlayerAvatar extends GameObject {
	
	private int lives;
	private Weapon weapon;
	
	
	public PlayerAvatar(Point position, int initialLives) {
		super(new Rectangle(
				position,
				ImageLoader.playerAvatarImage.getWidth(),
				ImageLoader.playerAvatarImage.getHeight()));
		
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
		g.drawImage(ImageLoader.playerAvatarImage, shape.getPosition().x, shape.getPosition().y, null);
	}

}
