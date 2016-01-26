package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Rectangle;

/**
 * This class represents standard missile used in game.
 * @author Karolina
 *
 */
public class StandardMissile extends Missile {
	
	/**
	 * This is width of the standard missile.
	 */
	public final static int WIDTH = 7;
	/**
	 * This is height of the standard missile.
	 */
	public final static int HEIGHT = 20;

	/**
	 * Constructs the standard missile 
	 * @param position Initial position of the missile
	 * @param gameWorldSize Abstract game world size
	 */
	public StandardMissile(Point2D position, Dimension gameWorldSize) {
		super(
			new Rectangle(
				position,
					new Dimension(WIDTH,	
							HEIGHT)),
			gameWorldSize);
	}
	
	/**
	 * Draws the standard missile.
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.getInstance().standardMissileImage,
			shape.getIntX(),
			shape.getIntY(),
			getWidth(),
			getHeight(),
			null);
	}
	
	/**
	 * Splits balls when collision is detected.
	 */
	@Override
	public CollisionOutcome collisionOutcome(GameObject b) {
		return CollisionOutcome.SPLIT;
	}
	
	/**
	 * Returns the velocity of the standard missile.
	 */
	@Override
	public double getVelocity() {
		return 0.25;
	}
	
	/**
	 * Returns the width of the standard missile
	 */
	@Override
	protected int getWidth() {
		return WIDTH;
	}
	
	/**
	 * Returns the height of the standard missile
	 */
	@Override
	protected int getHeight() {
		return HEIGHT;
	}
}
