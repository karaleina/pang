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

/**
 * This singleton class contains avatar properties (such as size and numbers of lives) and describe its move.
 */
public class PlayerAvatar extends GameObject {

	private int lives;
	private int width;
	private int height;
	private Weapon weapon;
	private double velocity;

	private static PlayerAvatar instance = null;

	/**
	 * @return Access to the instance.
     */
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

	/**
	 *	Private constructor. All parameters should be taken from GlobalConstants static class and the ImageLoader.
     */
	private PlayerAvatar(int initialLives, Dimension gameWorldSize, int width, int height) {
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

	public void reload() {
		instance = new PlayerAvatar(
				GlobalConstants.initialLives,
				GlobalConstants.GAME_WORLD_SIZE,
				ImageLoader.getInstance().playerAvatarWidth,
				ImageLoader.getInstance().playerAvatarHeight);
	}

	/**
	 * This method should by only called when graphic theme has changed.
	 */
	public void resize(){
		this.width  = ImageLoader.getInstance().playerAvatarWidth;
		this.height = ImageLoader.getInstance().playerAvatarHeight;
		shape = new Rectangle(
				new Point2D.Double(
						GlobalConstants.GAME_WORLD_SIZE.width / 2 - width / 2,
						GlobalConstants.GAME_WORLD_SIZE.height - height	),
				ImageLoader.getInstance().playerAvatarDimensions);
	}
	public int getWidth() {return width;}
	public int getHeight() {return height;}

	/**
	 * @return Current number of lives.
     */
	public int getLives() {
		return lives;
	}

	/**
	 * Add a new life.
	 */
	public void incrementLives() {
		this.lives++;
	}

	/**
	 * Loose a (single) life.
	 */
	public void decrementLives(){
		this.lives--;
	}

	/**
	 *
	 * @return Weapon, which is either StandardWeapon or SuperWeapon.
     */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Set weapon as either StandardWeapon or SuperWeapon.
	 * @param weapon
     */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Rectangle getRectangularShape() {
		return (Rectangle) shape;
	}
	
	public Missile shoot() {
		return weapon.shoot();
	}

	/**
	 * @param velocity
     */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	/**
	 * This
	 * @param playerPosition It must be "left", "center" or "right".
	 * @param gameLevelSize Size of game level (can vary from one to another)
     */
	public void setPosition(String playerPosition, Dimension gameLevelSize) {

		if(playerPosition.equals("left")) {
			this.moveTo(0, gameLevelSize.height - this.height);
		} else if(playerPosition.equals("center")) {
			this.moveTo(
					gameLevelSize.width / 2 - this.width / 2,
					gameLevelSize.height - this.height);
		} else if(playerPosition.equals("right")) {
			this.moveTo(
					gameLevelSize.width - this.width,
					gameLevelSize.height - this.height);
		} else {
			throw new RuntimeException("Unexpected player position in level config! Expected: left|center|right");
		}
	}

	/**
	 * 
	 * @param dt time interval (milliseconds)
     */
	@Override
	public void move(double dt) {
		double newX = shape.getExactX() + velocity * dt;
		if(newX >= 0 && newX <= gameWorldSize.getWidth() - this.width)
			moveTo(newX, shape.getExactY());
		else if(newX < 0)
			moveTo(0, shape.getExactY());
		else if(newX > gameWorldSize.getWidth() - this.width)
			moveTo(gameWorldSize.getWidth() - this.width, shape.getExactY());
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
			width,
			height,
			null);
	}

}
