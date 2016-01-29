package org.eiti.java.pang.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;

/**
 * This class draws a heart, which can increment number of lives.
 */
public class Heart extends ExtraObject {

	private int width;
	private int height;

	/**
	 *
	 * @param position
	 * @param width
	 * @param height
	 * @param gameWorldSize
     */
	public Heart(Point2D position, int width, int height, Dimension gameWorldSize) {
		super(
			ExtraObjectType.heart,
			new Rectangle(
					position,
					new Dimension(width, height)),
			gameWorldSize);
		this.width = width;
		this.height = height;
	}

	/**
	 * Draws a heart
	 * @param g Graphic context.
     */
	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.getInstance().heartImage,
			shape.getIntX(),
			shape.getIntY(),
			width,
			height,
			null);
	}

	/**
	 * When a Heart interact with instance of PlayerAvatar (which is static), nubbers of PlayerAvatar's lives increments.
	 */
	@Override
	public void interactWithPlayerAvatar() {
		PlayerAvatar.getInstance().incrementLives();
	}

}
