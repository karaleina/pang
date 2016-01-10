package org.eiti.java.pangserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.regex.Pattern;

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
			Scanner sc = new Scanner(inputStream, "utf-8");
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
		if(matches(requestContent, CheckLevelExistenceRequest.requestPattern)) {
			checkLevelExistence(requestContent);
		} else if(matches(requestContent, GetBestScoresRequest.requestPattern)) {
			getBestScores();
		} else if(matches(requestContent, GetGlobalConfigurationRequest.requestPattern)) {
			getGlobalConfiguration();
		} else if(matches(requestContent, GetLevelConfigurationRequest.requestPattern)) {
			getLevelConfiguration(requestContent);
		} else if(matches(requestContent, SaveScoreRequest.requestPattern)) {
			saveScore(requestContent);
		} else {
			throw new RuntimeException("Unknown request!");
		}
	}
	
	private boolean matches(String requestContent, Pattern pattern) {
		return pattern.matcher(requestContent).matches();
	}
	
	private void checkLevelExistence(String requestContent) throws Exception {
		CheckLevelExistenceRequest request = new CheckLevelExistenceRequest(requestContent);
		int levelNumber = Integer.parseInt(request.getParameters().get(0));
		boolean fileExists = new File("res/serverConfig/level" + levelNumber + ".xml").exists();
		outputStream.writeBoolean(fileExists);
	}
	
	private void getBestScores() throws Exception {
		BestScoresManager.sendBestScores(outputStream);
	}
	
	private void getGlobalConfiguration() throws Exception {
		FileSender.sendFile(
			new File("res/serverConfig/global.xml"),
			outputStream);
	}
	
	private void getLevelConfiguration(String requestContent) throws Exception {
		GetLevelConfigurationRequest request = new GetLevelConfigurationRequest(requestContent);
		int levelNumber = Integer.parseInt(request.getParameters().get(0));
		FileSender.sendFile(
			new File("res/serverConfig/level" + levelNumber + ".xml"),
			outputStream);
		System.out.println("level " + levelNumber + " configuration sent!");
	}
	
	private void saveScore(String requestContent) throws Exception {
		SaveScoreRequest request = new SaveScoreRequest(requestContent);
		String nickname = request.getParameters().get(0);
		int score = Integer.valueOf(request.getParameters().get(1));
		BestScoresManager.updateBestScores(nickname, score);
	}

}
