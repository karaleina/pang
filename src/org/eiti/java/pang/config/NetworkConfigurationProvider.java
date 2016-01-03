package org.eiti.java.pang.config;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.config.xml.XMLGameLevelConfiguration;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;
import org.eiti.java.pang.network.CheckLevelExistenceRequest;
import org.eiti.java.pang.network.GetBestScoresRequest;
import org.eiti.java.pang.network.GetGlobalConfigurationRequest;
import org.eiti.java.pang.network.GetLevelConfigurationRequest;
import org.eiti.java.pang.network.SaveScoreRequest;
import org.eiti.java.pang.network.ServerConnection;

public class NetworkConfigurationProvider implements ConfigurationProvider {
	
	private final ServerConnection connection;
	
	private DataInputStream inputStream;
	
	public NetworkConfigurationProvider(ServerConnection connection) throws Exception {
		this.connection = connection;
		this.inputStream = new DataInputStream(connection.getInputStream());
	}

	@Override
	public XMLBestScoresIO getBestScores() throws Exception {
		connection.sendRequest(new GetBestScoresRequest());
		return new XMLBestScoresIO(new ByteArrayInputStream(receiveFile()));
	}
	
	@Override
	public void updateBestScores(String nickname, int score) throws Exception {
		connection.sendRequest(new SaveScoreRequest(nickname, score));
	}

	@Override
	public XMLGlobalConfiguration getGlobalConfiguration() throws Exception {
		connection.sendRequest(new GetGlobalConfigurationRequest());
		return new XMLGlobalConfiguration(new ByteArrayInputStream(receiveFile()));
	}

	@Override
	public boolean levelExists(int levelNumber) throws Exception {
		connection.sendRequest(new CheckLevelExistenceRequest(levelNumber));
		return inputStream.readBoolean();
	}

	@Override
	public XMLGameLevelConfiguration getLevelConfiguration(int levelNumber) throws Exception {
		connection.sendRequest(new GetLevelConfigurationRequest(levelNumber));
		return new XMLGameLevelConfiguration(new ByteArrayInputStream(receiveFile()));
	}
	
	private byte[] receiveFile() throws Exception {
		int fileSize = inputStream.readInt();
		byte[] buffer = new byte[fileSize];
		inputStream.readFully(buffer);
		return buffer;
	}

}
