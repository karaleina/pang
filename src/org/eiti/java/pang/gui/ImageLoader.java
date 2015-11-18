package org.eiti.java.pang.gui;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage background;
	
	public static BufferedImage playerAvatarImage;
	
	public static BufferedImage standardMissileImage;
	
	public static BufferedImage superMissileImage;
	
	public static BufferedImage superWeaponImage;
	
	public static BufferedImage heartImage;
		
	static {
		try {
			background = ImageIO.read(new FileInputStream("res/images/background.jpg"));
			heartImage = ImageIO.read(new FileInputStream("res/images/heart.png"));
			playerAvatarImage = ImageIO.read(new FileInputStream("res/images/player.png"));
			standardMissileImage = ImageIO.read(new FileInputStream("res/images/old-bullet.png"));
			superMissileImage = ImageIO.read(new FileInputStream("res/images/super-bullet.png"));
			superWeaponImage = ImageIO.read(new FileInputStream("res/images/mauser.png"));
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}	
	
}
