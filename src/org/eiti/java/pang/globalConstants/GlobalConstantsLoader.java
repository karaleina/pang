package org.eiti.java.pang.globalConstants;


import org.eiti.java.pang.config.XMLGlobalConfiguration;
import org.eiti.java.pang.config.XMLImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;

public class GlobalConstantsLoader {
	
	public static String title;
	public static Dimension gameWindowSize;
    public static int initialLives;
    public static double gravity;
    public static double playerVelocity;
    public static long minTimeBetweenShots;
	public static String bestScoresPath;
		
	static {
		try {
			XMLGlobalConfiguration configuration = new XMLGlobalConfiguration("res/config/global.xml");
			title = configuration.getTitle();
            gameWindowSize = configuration.getGameWindowSize();
            initialLives = configuration.getLives();
            gravity = configuration.getGravity();
			playerVelocity = configuration.getPlayerVelocity();
			minTimeBetweenShots = configuration.getMinTimeBetweenShots();
			bestScoresPath = configuration.getBestScoresPath();
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}	
	
}
