package org.eiti.java.pang.model.weapons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.shapes.Rectangle;

/**
 * This class represents super missile
 * which can destroy all balls by once. 
 * @author Karolina
 *
 */
public class SuperMissile extends Missile {

	/**
	 * Super-missile's width
	 */
	private int width;
	/** 
	 * Super-missile's height
	 */
	private int height;
	
	/**
	 * Constructs the super-missile object.
	 * @param position Initial position of the missile
	 * @param width Missile's width 
	 * @param height Missile's height
	 * @param gameWorldSize Abstract game world size
	 */
	public SuperMissile(Point2D position, int width, int height, Dimension gameWorldSize) {
		super(
			new Rectangle(
				position,
				new Dimension(width,
				height)),
			gameWorldSize);
		this.width = width;
		this.height = height;
	}

	/**
	 * Draws a super missile.
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.getInstance().superMissileImage,
			shape.getIntX(),
			shape.getIntY(),
			width,
			height,
			null);
	}

	/**
	 * Destroy balls when collision outcomes
	 */
	@Override
	public CollisionOutcome collisionOutcome(GameObject b) {
		return CollisionOutcome.DESTROY;
	}
	
	/**
	 * Returns velocity of the missile
	 */
	@Override
	public double getVelocity() {
		return 0.4;
	}
	
	/**
	 * Returns width of the missile
	 */
	@Override
	protected int getWidth() {
		return width;
	}
	
	/**
	 * Returns height of the missile
	 */
	@Override
	protected int getHeight() {
		return height;
	}

}
