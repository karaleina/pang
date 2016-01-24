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

/**
 * This is a thread at server which responses to client's requests.
 * @author Karolina
 *
 */
public class ClientThread extends Thread {
	
	/**
	 * This is a socket which enables to communicate with the client.
	 */
	private Socket socket;
	
	/**
	 * This is an input stream from the client.
	 */
	private InputStream inputStream;
	
	/**
	 * This is an output stream to the client.
	 */
	private DataOutputStream outputStream;
	
	/**
	 * This is a field indicating whether the thread is running.
	 */
	private volatile boolean running = false;
	
	/**
	 * This creates a new client thread.
	 * @param socket This is a client socket 
	 */
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	/**
	 * The main client thread loop consists of receiving requests
	 * from the client and responding to them.
	 */
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
	
	/**
	 * This method initializes input and output streams.
	 * For output stream, a DataOutputStream wrapper is used
	 * to make serializing numbers and booleans easier.
	 */
	private void initializeStreams() {
		try {
			this.inputStream = socket.getInputStream();
			this.outputStream = new DataOutputStream(socket.getOutputStream());
		} catch(Exception exc) {
			throw new RuntimeException("failed to initialize streams!");
		}
	}
	
	/**
	 * Check if the client thread is running.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * This method matches request content to a specific request type.
	 * @param requestContent Raw request content as a UTF-8 encoded string
	 * @throws Exception
	 */
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
	
	/**
	 * This helper method checks if a request matches given regex.
	 * @param requestContent Raw request content
	 * @param pattern Regex pattern
	 */
	private boolean matches(String requestContent, Pattern pattern) {
		return pattern.matcher(requestContent).matches();
	}
	
	/**
	 * This method checks if a level exists in the server configuration
	 * and sends the boolean value to the client.
	 * The requested level number is encoded in the request content.
	 * 
	 * @param requestContent Raw request content
	 * @throws Exception
	 */
	private void checkLevelExistence(String requestContent) throws Exception {
		CheckLevelExistenceRequest request = new CheckLevelExistenceRequest(requestContent);
		int levelNumber = Integer.parseInt(request.getParameters().get(0));
		boolean fileExists = new File("res/serverConfig/level" + levelNumber + ".xml").exists();
		outputStream.writeBoolean(fileExists);
	}
	
	/**
	 * This method sends a list of best scores to the client.
	 * @throws Exception
	 */
	private void getBestScores() throws Exception {
		BestScoresManager.sendBestScores(outputStream);
	}
	
	/**
	 * This method sends global configuration to the client.
	 * @throws Exception
	 */
	private void getGlobalConfiguration() throws Exception {
		FileSender.sendFile(
			new File("res/serverConfig/global.xml"),
			outputStream);
	}
	
	/**
	 * This method sends requested level configuration to the client.
	 * Requested level's number is encoded in request content.
	 * 
	 * @param requestContent Raw request content
	 * @throws Exception
	 */
	private void getLevelConfiguration(String requestContent) throws Exception {
		GetLevelConfigurationRequest request = new GetLevelConfigurationRequest(requestContent);
		int levelNumber = Integer.parseInt(request.getParameters().get(0));
		FileSender.sendFile(
			new File("res/serverConfig/level" + levelNumber + ".xml"),
			outputStream);
		System.out.println("level " + levelNumber + " configuration sent!");
	}
	
	/**
	 * This method saves the score received from the client.
	 * Player nickname and numeric score are encoded in request content.
	 * 
	 * @param requestContent Raw request content
	 * @throws Exception
	 */
	private void saveScore(String requestContent) throws Exception {
		SaveScoreRequest request = new SaveScoreRequest(requestContent);
		String nickname = request.getParameters().get(0);
		int score = Integer.valueOf(request.getParameters().get(1));
		BestScoresManager.updateBestScores(nickname, score);
	}

}
