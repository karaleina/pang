package org.eiti.java.pang.loaders;


import org.eiti.java.pang.config.XMLImagePaths;

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
			XMLImagePaths paths = new XMLImagePaths("res/config/imagepaths.xml");
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
