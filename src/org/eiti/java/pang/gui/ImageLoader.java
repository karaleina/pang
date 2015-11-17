package org.eiti.java.pang.gui;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage playerAvatarImage;
	
	public static BufferedImage superMissileImage;
	
	public static BufferedImage superWeaponImage;
	
	public static BufferedImage heartImage;
		
	static {
		try {
			
			heartImage = ImageIO.read(new FileInputStream("res/images/heart.png"));
			playerAvatarImage = ImageIO.read(new FileInputStream("res/images/player.png"));
			superMissileImage = ImageIO.read(new FileInputStream("res/images/bullet.png"));
			superWeaponImage = ImageIO.read(new FileInputStream("res/images/mauser.jpg"));
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}	
	
}
