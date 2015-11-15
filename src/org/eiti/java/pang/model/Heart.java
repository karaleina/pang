package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;

import org.eiti.java.pang.model.shapes.Rectangle;

public class Heart extends ExtraObject {

	private final static int HEART_WIDTH = 40;
	private final static int HEART_HEIGHT = 40;
	
	public Heart(Point position) {
		super(new Rectangle(
				position,
				HEART_WIDTH,
				HEART_HEIGHT));
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void interactWith(PlayerAvatar player) {
		player.setLives(player.getLives() + 1);
	}

}
