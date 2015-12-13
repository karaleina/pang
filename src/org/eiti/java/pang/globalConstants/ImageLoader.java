package org.eiti.java.pang.globalConstants;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.eiti.java.pang.config.XMLImageLoader;

public class ImageLoader {
	
	public static BufferedImage background;
	public static BufferedImage playerAvatarImage;
	public static BufferedImage standardMissileImage;
	public static BufferedImage superMissileImage;
	public static BufferedImage superWeaponImage;
	public static BufferedImage heartImage;
		
	static {
		try {
			XMLImageLoader paths = new XMLImageLoader("res/config/imagepaths.xml");
			background = 		ImageIO.read(new FileInputStream(paths.getBackgroundPath()));
			heartImage = 		ImageIO.read(new FileInputStream(paths.getHeartImagePath()));
			playerAvatarImage = ImageIO.read(new FileInputStream(paths.getPlayerAvatarImage()));
			standardMissileImage = ImageIO.read(new FileInputStream(paths.getStandardMissileImage()));
			superMissileImage = ImageIO.read(new FileInputStream(paths.getSuperMissileImage()));
			superWeaponImage = 	ImageIO.read(new FileInputStream(paths.getSuperWeaponImage()));
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}	
	
}
