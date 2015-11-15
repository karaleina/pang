package org.eiti.java.pang.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.eiti.java.pang.model.shapes.Rectangle;

public class Heart extends ExtraObject {

	public static int HEART_WIDTH;
	public static int HEART_HEIGHT;
	
	private static BufferedImage heartImage;
	
	static {
		try {
			heartImage = ImageIO.read(new FileInputStream("res/images/heart.png"));
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		HEART_WIDTH = heartImage.getWidth();
		HEART_HEIGHT = heartImage.getHeight();
	}
	
	public Heart(Point position) {
		super(new Rectangle(
				position,
				HEART_WIDTH,
				HEART_HEIGHT));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(heartImage, shape.getPosition().x, shape.getPosition().y, null);
	}

	@Override
	public void interactWith(PlayerAvatar player) {
		player.setLives(player.getLives() + 1);
	}

}
