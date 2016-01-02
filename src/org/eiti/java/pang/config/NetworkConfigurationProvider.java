package org.eiti.java.pang.config;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.config.xml.XMLGameLevelConfiguration;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;
import org.eiti.java.pang.network.CheckLevelExistenceRequest;
import org.eiti.java.pang.network.GetBestScoresRequest;
import org.eiti.java.pang.network.GetGlobalConfigurationRequest;
import org.eiti.java.pang.network.GetLevelConfigurationRequest;
import org.eiti.java.pang.network.ServerConnection;

public class NetworkConfigurationProvider implements ConfigurationProvider {
	
	private final ServerConnection connection;
	
	public NetworkConfigurationProvider(ServerConnection connection) {
		this.connection = connection;
	}

	@Override
	public XMLBestScoresIO getBestScores() throws Exception {
		connection.sendRequest(new GetBestScoresRequest());
		return new XMLBestScoresIO(connection.getInputStream());
	}

	@Override
	public XMLGlobalConfiguration getGlobalConfiguration() throws Exception {
		connection.sendRequest(new GetGlobalConfigurationRequest());
		return new XMLGlobalConfiguration(connection.getInputStream());
	}

	@Override
	public boolean levelExists(int levelNumber) throws Exception {
		connection.sendRequest(new CheckLevelExistenceRequest(levelNumber));
		return connection.getInputStream().read() == '1';
	}

	@Override
	public XMLGameLevelConfiguration getLevelConfiguration(int levelNumber) throws Exception {
		connection.sendRequest(new GetLevelConfigurationRequest(levelNumber));
		return new XMLGameLevelConfiguration(connection.getInputStream());
	}

}
