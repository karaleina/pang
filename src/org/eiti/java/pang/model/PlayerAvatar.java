package org.eiti.java.pang.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.StandardWeapon;
import org.eiti.java.pang.model.weapons.Weapon;

public class PlayerAvatar extends GameObject {
	
	private int lives;
	private Weapon weapon;
	
	private double velocity;

	public static int getWidth() {
		return 60;
	}

	public static int getHeight() {
		return 160;
	}
	
	public PlayerAvatar(Point2D position, int initialLives, Dimension gameWorldSize) {
		super(new Rectangle(
				position,
				getWidth(),
				getHeight()),
				gameWorldSize);
		
		this.lives = initialLives;
		this.weapon = new StandardWeapon(position, gameWorldSize);
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
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public void move(double dt) {
		double newX = shape.getExactX() + velocity * dt;
		if(newX >= 0 && newX <= gameWorldSize.getWidth() - getWidth()) {
			moveTo(newX, shape.getExactY());
		}
	}

	public void moveTo(double x, double y) {
		shape.moveTo(x, y);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.getInstance().playerAvatarImage,
			shape.getIntX(),
			shape.getIntY(),
			getWidth(),
			getHeight(),
			null);
	}

}
