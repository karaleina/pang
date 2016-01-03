package org.eiti.java.pang.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.config.xml.XMLGameLevelConfiguration;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;

public class LocalConfigurationProvider implements ConfigurationProvider {

	private final String bestScoresPath = "res/config/bestScoresExample.xml";
	
	private final String globalConfigPath = "res/config/global.xml";

	private final XMLGlobalConfiguration globalConfiguration;

	public LocalConfigurationProvider() throws Exception {
		globalConfiguration =
			new XMLGlobalConfiguration(new FileInputStream(globalConfigPath));
	}
	
	@Override
	public synchronized XMLBestScoresIO getBestScores() throws Exception {
		return new XMLBestScoresIO(new FileInputStream(bestScoresPath));
	}

	@Override
	public synchronized void updateBestScores(String nickname, int score) throws Exception {
		XMLBestScoresIO bestScores = getBestScores();
		bestScores.update(nickname, score);
		FileOutputStream outputStream = new FileOutputStream(bestScoresPath);
		bestScores.save(outputStream);
		outputStream.close();
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
