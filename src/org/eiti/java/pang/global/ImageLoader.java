package org.eiti.java.pang.global;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.xpath.XPathExpressionException;

import org.eiti.java.pang.config.xml.XMLImageLoader;

public class ImageLoader {
	
	public  BufferedImage background;
	public  BufferedImage playerAvatarImage;
	public  BufferedImage standardMissileImage;
	public  BufferedImage superMissileImage;
	public  BufferedImage superWeaponImage;
	public  BufferedImage heartImage;

	private static ImageLoader instance = null;	//a singleton
	public static ImageLoader getInstance() {
		if(instance == null) {
			instance = new ImageLoader();
			instance.update("res/config/theme1.xml");
		}
		return instance;
	}

	private ImageLoader() {
	}


	public void update(String themeConfigPath) {
		try {
			XMLImageLoader paths = new XMLImageLoader(themeConfigPath);
			background = ImageIO.read(new FileInputStream(paths.getBackgroundPath()));
			heartImage = ImageIO.read(new FileInputStream(paths.getHeartImagePath()));
			playerAvatarImage = ImageIO.read(new FileInputStream(paths.getPlayerAvatarImage()));
			standardMissileImage = ImageIO.read(new FileInputStream(paths.getStandardMissileImage()));
			superMissileImage = ImageIO.read(new FileInputStream(paths.getSuperMissileImage()));
			superWeaponImage = ImageIO.read(new FileInputStream(paths.getSuperWeaponImage()));

		} catch (FileNotFoundException exc) {
			exc.printStackTrace();
		} catch (IOException exc) {
			exc.printStackTrace();
		} catch (XPathExpressionException exc) {
			exc.printStackTrace();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}
	
}
