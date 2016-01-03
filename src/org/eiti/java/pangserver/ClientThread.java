package org.eiti.java.pangserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.network.CheckLevelExistenceRequest;
import org.eiti.java.pang.network.GetBestScoresRequest;
import org.eiti.java.pang.network.GetGlobalConfigurationRequest;
import org.eiti.java.pang.network.GetLevelConfigurationRequest;
import org.eiti.java.pang.network.SaveScoreRequest;

public class ClientThread extends Thread {
	
	private Socket socket;
	
	private InputStream inputStream;
	
	private DataOutputStream outputStream;
	
	private volatile boolean running = false;
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		running = true;
		initializeStreams();
		try {
			Scanner sc = new Scanner(inputStream);
			while(sc.hasNextLine()) {
				String command = sc.nextLine();
				System.out.println("received: " + command);
				processRequest(command);
			}
			sc.close();
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		running = false;
	}
	
	private void initializeStreams() {
		try {
			this.inputStream = socket.getInputStream();
			this.outputStream = new DataOutputStream(socket.getOutputStream());
		} catch(Exception exc) {
			throw new RuntimeException("failed to initialize streams!");
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	private void processRequest(String requestContent) throws Exception {
		if(CheckLevelExistenceRequest.requestPattern.matcher(requestContent).matches()) {
			checkLevelExistence(new CheckLevelExistenceRequest(requestContent));
		} else if(GetBestScoresRequest.requestPattern.matcher(requestContent).matches()) {
			getBestScores(new GetBestScoresRequest(requestContent));
		} else if(GetGlobalConfigurationRequest.requestPattern.matcher(requestContent).matches()) {
			getGlobalConfiguration(new GetGlobalConfigurationRequest(requestContent));
		} else if(GetLevelConfigurationRequest.requestPattern.matcher(requestContent).matches()) {
			getLevelConfiguration(new GetLevelConfigurationRequest(requestContent));
		} else if(SaveScoreRequest.requestPattern.matcher(requestContent).matches()) {
			saveScore(new SaveScoreRequest(requestContent));
		} else {
			throw new RuntimeException("Unknown request!");
		}
	}
	
	private void checkLevelExistence(CheckLevelExistenceRequest request) throws Exception {
		int levelNumber = Integer.parseInt(request.getParameters().get(0));
		boolean fileExists = new File("res/serverConfig/level" + levelNumber + ".xml").exists();
		outputStream.writeBoolean(fileExists);
	}
	
	private void getBestScores(GetBestScoresRequest request) throws Exception {
		BestScoresManager.sendBestScores(outputStream);
	}
	
	private void getGlobalConfiguration(GetGlobalConfigurationRequest request) throws Exception {
		FileSender.sendFile(
			new File("res/serverConfig/global.xml"),
			outputStream);
	}
	
	private void getLevelConfiguration(GetLevelConfigurationRequest request) throws Exception {
		int levelNumber = Integer.parseInt(request.getParameters().get(0));
		FileSender.sendFile(
			new File("res/serverConfig/level" + levelNumber + ".xml"),
			outputStream);
		System.out.println("level " + levelNumber + " configuration sent!");
	}
	
	private void saveScore(SaveScoreRequest request) throws Exception {
		String nickname = request.getParameters().get(0);
		int score = Integer.valueOf(request.getParameters().get(1));
		BestScoresManager.updateBestScores(nickname, score);
	}

}
