package org.eiti.java.pang.game;

public class GameInitParameters {

	private final String nickname;
	
	private final boolean local;
	
	private final String serverAddress;
	
	private final int serverPort;
	
	public static GameInitParameters local(String nickname) {
		return new GameInitParameters(nickname, true, null, 0);
	}
	
	public static GameInitParameters network(String nickname, String serverAddress, int serverPort) {
		return new GameInitParameters(nickname, false, serverAddress, serverPort);
	}
	
	private GameInitParameters(String nickname, boolean local, String serverAddress, int serverPort) {
		this.nickname = nickname;
		this.local = local;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public boolean isLocal() {
		return local;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}
	
}
