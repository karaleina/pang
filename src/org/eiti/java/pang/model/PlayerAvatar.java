package org.eiti.java.pang.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.GlobalConstants;
import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.StandardWeapon;
import org.eiti.java.pang.model.weapons.Weapon;

public class PlayerAvatar extends GameObject {
	
	private int lives;
	private int width;
	private int height;
	private Weapon weapon;
	private double velocity;

	private static PlayerAvatar instance = null;
	public static  PlayerAvatar getInstance() {
		if (instance == null) {
			instance = new PlayerAvatar(
					GlobalConstants.initialLives,
					GlobalConstants.GAME_WORLD_SIZE,
					ImageLoader.getInstance().playerAvatarWidth,
					ImageLoader.getInstance().playerAvatarHeight);
		}
		//instance.updateSize();
		return instance;
	}

	public PlayerAvatar(int initialLives, Dimension gameWorldSize, int width, int height) {
		super(new Rectangle(
				new Point2D.Double(
						GlobalConstants.GAME_WORLD_SIZE.width / 2 - width / 2,
						GlobalConstants.GAME_WORLD_SIZE.height - height
				),
				ImageLoader.getInstance().playerAvatarDimensions),
				gameWorldSize);
		this.width  = ImageLoader.getInstance().playerAvatarWidth;
		this.height = ImageLoader.getInstance().playerAvatarHeight;
		this.lives = initialLives;
		this.weapon = new StandardWeapon(new Point2D.Double(0,0), gameWorldSize);
	}

	public void resize(){
		this.width  = ImageLoader.getInstance().playerAvatarWidth;
		this.height = ImageLoader.getInstance().playerAvatarHeight;
	}
	public int getWidth() {return width;}
	public int getHeight() {return height;}

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
