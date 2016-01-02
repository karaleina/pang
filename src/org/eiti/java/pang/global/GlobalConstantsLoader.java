package org.eiti.java.pang.global;


import java.awt.Dimension;
import java.io.FileInputStream;

import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;

public class GlobalConstantsLoader {
	
	public static String title;
	public static Dimension gameWindowSize;
    public static int initialLives;
    public static double gravity;
    public static double playerVelocity;
    public static long minTimeBetweenShots;
		
	static {
		try {
			XMLGlobalConfiguration configuration = new XMLGlobalConfiguration(
				new FileInputStream("res/config/global.xml"));
			setConstants(configuration);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}	
	
	public static void setConstants(XMLGlobalConfiguration configuration) throws Exception {
		title = configuration.getTitle();
        gameWindowSize = configuration.getGameWindowSize();
        initialLives = configuration.getLives();
        gravity = configuration.getGravity();
		playerVelocity = configuration.getPlayerVelocity();
		minTimeBetweenShots = configuration.getMinTimeBetweenShots();
	}
	
}
