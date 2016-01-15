package org.eiti.java.pang.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;

public class Heart extends ExtraObject {

	private int width;
	private int height;
	
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

	@Override
	public void interactWith(PlayerAvatar player) {
		player.setLives(player.getLives() + 1);
	}

}
