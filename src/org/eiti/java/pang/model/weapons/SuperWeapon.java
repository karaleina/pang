package org.eiti.java.pang.model.weapons;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.shapes.Rectangle;

public class SuperWeapon extends Weapon {

	public static int SUPER_WEAPON_WIDTH;
	public static int SUPER_WEAPON_HEIGHT;
	
	private static BufferedImage weaponImage;
	
	static {
		try {
			weaponImage = ImageIO.read(new FileInputStream("res/images/mauser.jpg"));
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		SUPER_WEAPON_WIDTH = weaponImage.getWidth();
		SUPER_WEAPON_HEIGHT = weaponImage.getHeight();
	}
	
	public SuperWeapon(Point position) {
		super(new Rectangle(
				position,
				SUPER_WEAPON_WIDTH,
				SUPER_WEAPON_HEIGHT));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(weaponImage, shape.getPosition().x, shape.getPosition().y, null);
	}

	@Override
	public Missile shoot(PlayerAvatar shooter) {
		return new SuperMissile(
			calculateMissilePosition(
				shooter,
				SuperMissile.SUPER_MISSILE_WIDTH,
				SuperMissile.SUPER_MISSILE_HEIGHT));
	}

}
