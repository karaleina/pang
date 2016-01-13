package org.eiti.java.pang.global;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.xpath.XPathExpressionException;

import org.eiti.java.pang.config.xml.XMLImageLoader;

public class ImageLoader {
	
	public BufferedImage background;
	public BufferedImage playerAvatarImage;
	public BufferedImage standardMissileImage;
	public BufferedImage superMissileImage;
	public BufferedImage superWeaponImage;
	public BufferedImage heartImage;
	public Dimension playerAvatarDimensions;
	public int playerAvatarWidth;
	public int playerAvatarHeight;

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
			XMLImageLoader config = new XMLImageLoader(themeConfigPath);
			background = ImageIO.read(new FileInputStream(config.getBackgroundPath()));
			heartImage = ImageIO.read(new FileInputStream(config.getHeartImagePath()));
			playerAvatarImage = ImageIO.read(new FileInputStream(config.getPlayerAvatarImage()));
			standardMissileImage = ImageIO.read(new FileInputStream(config.getStandardMissileImage()));
			superMissileImage = ImageIO.read(new FileInputStream(config.getSuperMissileImage()));
			superWeaponImage = ImageIO.read(new FileInputStream(config.getSuperWeaponImage()));

			playerAvatarDimensions = config.getPlayerAvatarDim();
			playerAvatarWidth = config.getPlayerAvatarWidth();
			playerAvatarHeight = config.getPlayerAvatarHeight();

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
