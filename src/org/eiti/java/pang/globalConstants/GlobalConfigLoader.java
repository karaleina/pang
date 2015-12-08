package org.eiti.java.pang.globalConstants;


import org.eiti.java.pang.config.XMLGlobalConfiguration;
import org.eiti.java.pang.config.XMLImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class GlobalConfigLoader {
	
	public static String title;
	public static Dimension gameWindowSize;
    public static int initialLives;
    public static double gravity;
		
	static {
		try {
			XMLGlobalConfiguration configuration = new XMLGlobalConfiguration("res/config/global.xml");
			title = configuration.getTitle();
            gameWindowSize = configuration.getGameWindowSize();
            initialLives = configuration.getLives();
            gravity = configuration.getGravity();
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}	
	
}
