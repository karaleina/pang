package org.eiti.java.pang.config;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.config.xml.XMLGameLevelConfiguration;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;

public interface ConfigurationProvider {
	
	public XMLBestScoresIO getBestScores() throws Exception;
	
	public void updateBestScores(String nickname, int score) throws Exception;
	
	public XMLGlobalConfiguration getGlobalConfiguration() throws Exception;
	
	public boolean levelExists(int levelNumber) throws Exception;
	
	public XMLGameLevelConfiguration getLevelConfiguration(int levelNumber) throws Exception;

}
