package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.StandardWeapon;
import org.eiti.java.pang.model.weapons.Weapon;

public class PlayerAvatar extends GameObject {
	
	private int lives;
	private Weapon weapon;
	
	public static int PLAYER_AVATAR_WIDTH;
	public static int PLAYER_AVATAR_HEIGHT;
	
	private static BufferedImage playerImage;
	
	static {
		try {
			playerImage = ImageIO.read(new FileInputStream("res/images/player.png"));
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		PLAYER_AVATAR_WIDTH = playerImage.getWidth();
		PLAYER_AVATAR_HEIGHT = playerImage.getHeight();
	}
	
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
		g.drawImage(playerImage, shape.getPosition().x, shape.getPosition().y, null);
	}

}
