package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.shapes.Rectangle;

public class Heart extends ExtraObject {
	
	public Heart(Point position) {
		super(new Rectangle(
				position,
				ImageLoader.heartImage.getWidth(),
				ImageLoader.heartImage.getHeight()));
	}
	

	@Override
	public void draw(Graphics g) {
		g.drawImage(ImageLoader.heartImage, shape.getPosition().x, shape.getPosition().y, null);
	}

	@Override
	public void interactWith(PlayerAvatar player) {
		player.setLives(player.getLives() + 1);
	}

}
