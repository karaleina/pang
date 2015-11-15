package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperMissile extends Missile {
	
	public static int SUPER_MISSILE_WIDTH;
	public static int SUPER_MISSILE_HEIGHT;
	
	private static BufferedImage missileImage;
	
	static {
		try {
			missileImage = ImageIO.read(new FileInputStream("res/images/bullet.png"));
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		SUPER_MISSILE_WIDTH = missileImage.getWidth();
		SUPER_MISSILE_HEIGHT = missileImage.getHeight();
	}

	public SuperMissile(Point position) {
		super(new Rectangle(position, SUPER_MISSILE_WIDTH, SUPER_MISSILE_HEIGHT));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(missileImage, shape.getPosition().x, shape.getPosition().y, null);
	}

	@Override
	public CollisionOutcome collisionOutcome(Ball b) {
		return CollisionOutcome.DESTROY;
	}

}
