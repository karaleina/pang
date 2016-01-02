package org.eiti.java.pang.config;

import java.io.File;
import java.io.FileInputStream;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.config.xml.XMLGameLevelConfiguration;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;

public class LocalConfigurationProvider implements ConfigurationProvider {

	private final XMLBestScoresIO bestScoresConfiguration;

	private final XMLGlobalConfiguration globalConfiguration;

	public LocalConfigurationProvider() throws Exception {
		bestScoresConfiguration =
			new XMLBestScoresIO(new FileInputStream("res/config/bestScoresExample.xml"));
		globalConfiguration =
			new XMLGlobalConfiguration(new FileInputStream("res/config/global.xml"));
	}
	
	@Override
	public XMLBestScoresIO getBestScores() {
		return bestScoresConfiguration;
	}

	@Override
	public XMLGlobalConfiguration getGlobalConfiguration() {
		return globalConfiguration;
	}
	
	@Override
	public boolean levelExists(int levelNumber) {
		return new File(getLevelConfigurationPath(levelNumber)).exists();
	}

	@Override
	public XMLGameLevelConfiguration getLevelConfiguration(int levelNumber) {
		try {
			String configFilePath = getLevelConfigurationPath(levelNumber);
			return new XMLGameLevelConfiguration(new FileInputStream(configFilePath));
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	private String getLevelConfigurationPath(int levelNumber) {
		String levelPath = "res/config/level";
		levelPath += levelNumber;
		levelPath += ".xml";
		return levelPath;
	}
}
