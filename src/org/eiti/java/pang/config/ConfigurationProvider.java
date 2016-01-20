package org.eiti.java.pang.config;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.config.xml.XMLGameLevelConfiguration;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;

/**
 * This interface represents a source of configurations
 * that can be loaded at application runtime.
 * It should also be able to update the best scores list.
 * 
 * @author Karolina
 *
 */
public interface ConfigurationProvider {
	
	/**
	 * Returns the list of best scores.
	 * @throws Exception
	 */
	public XMLBestScoresIO getBestScores() throws Exception;
	
	/**
	 * Updates the list of best scores with given entry.
	 * If the score is too low, the entry may not be appended.
	 * 
	 * @param nickname Player nickname
	 * @param score Game score
	 * @throws Exception
	 */
	public void updateBestScores(String nickname, int score) throws Exception;
	
	/**
	 * Returns global configuration that provides application-wide
	 * constants, like game world resolution, gravity constant, etc.
	 * @throws Exception
	 */
	public XMLGlobalConfiguration getGlobalConfiguration() throws Exception;
	
	/**
	 * Check if level of given number exists.
	 * 
	 * @param levelNumber Level number to check
	 * @throws Exception
	 */
	public boolean levelExists(int levelNumber) throws Exception;
	
	/**
	 * Get level configuration for given level number. If the level
	 * does not exist, an exception is thrown.
	 * 
	 * @param levelNumber Level number to request for configuration
	 * @throws Exception
	 */
	public XMLGameLevelConfiguration getLevelConfiguration(int levelNumber) throws Exception;

}
