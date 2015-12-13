package org.eiti.java.pang.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;

public class Heart extends ExtraObject {
	
	public static int getWidth() {
		return 60;
	}
	
	public static int getHeight() {
		return 60;
	}
	
	public Heart(Point2D position, Dimension gameWorldSize) {
		super(
			ExtraObjectType.heart,
			new Rectangle(
				position,
				getWidth(),
				getHeight()),
			gameWorldSize);
	}
	

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.heartImage,
			shape.getIntX(),
			shape.getIntY(),
			getWidth(),
			getHeight(),
			null);
	}

	@Override
	public void interactWith(PlayerAvatar player) {
		player.setLives(player.getLives() + 1);
	}

}
