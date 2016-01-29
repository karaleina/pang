package org.eiti.java.pang.global;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.xpath.XPathExpressionException;

import org.eiti.java.pang.config.xml.XMLImageLoader;

/**
 * Stores images and its desired dimensions. Acces via getInstance() method (this is a singleton).
 */
public class ImageLoader {
	
	public BufferedImage background;
	public BufferedImage heartImage;
	public BufferedImage playerAvatarImage;
	public BufferedImage standardMissileImage;
	public BufferedImage superMissileImage;
	public BufferedImage superWeaponImage;

	public Dimension playerAvatarDimensions;

	public int heartWidth;
	public int heartHeight;
	public int playerAvatarWidth;
	public int playerAvatarHeight;
	public int standardMissileWidth;
	public int standardMissileHeight;
	public int superMissileWidth;
	public int superMissileHeight;
	public int superWeaponWidth;
	public int superWeaponHeight;

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

	/**
	 * Update should be done when graphic theme has been changed.
	 * @param themeConfigPath
     */
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

			heartWidth = config.getHeartWidth();
			heartHeight = config.getHeartHeight();
			playerAvatarWidth = config.getPlayerAvatarWidth();
			playerAvatarHeight = config.getPlayerAvatarHeight();
			standardMissileWidth = config.getStandardMissileWidth();
			standardMissileHeight = config.getStandardMissileHeight();
			superMissileWidth = config.getSuperMissileWidth();
			superMissileHeight = config.getSuperMissileHeight();
			superWeaponWidth = config.getSuperWeaponWidth();
			superWeaponHeight = config.getSuperWeaponHeight();

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
