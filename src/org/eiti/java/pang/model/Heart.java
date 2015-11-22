package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;

public class Heart extends ExtraObject {
	
	public static int getWidth() {
		return 60;
	}
	
	public static int getHeight() {
		return 60;
	}
	
	public Heart(Point position) {
		super(
			ExtraObjectType.heart,
			new Rectangle(
				position,
				getWidth(),
				getHeight()));
	}
	

	@Override
	public void draw(Graphics g) {
		g.drawImage(
			ImageLoader.heartImage,
			shape.getPosition().x,
			shape.getPosition().y,
			getWidth(),
			getHeight(),
			null);
	}

	@Override
	public void interactWith(PlayerAvatar player) {
		player.setLives(player.getLives() + 1);
	}

}
