package org.eiti.java.pang.network;

import java.util.regex.Pattern;

/**
 * This request is used to save score of the finished game. 
 * Example: Jack Sparrow 9999999999
 * @author Karolina
 */
public class SaveScoreRequest extends Request {
	
	/**
	 * This is request header.
	 */
	private final static String requestHeader = "SAVE_SCORE";
	
	/**
	 * This is a regex (regular expression)
	 * pattern for saving score.
	 * It contains a request header and
	 * a nick of the player(text) and a score (int).
	 */
	public final static Pattern requestPattern = Pattern.compile(requestHeader + " (.+) (\\d+)");

	/**
	 * It constructs a request from given content.
	 * It is used by the server.
	 */
	public SaveScoreRequest(String content) {
		super(requestPattern, content);
	}
	
	/**
	 * It constructs a reguest from given nickname and score.
	 * It is used by client.
	 * @param nickname This is a nickname of the player;
	 * it can include any characters.
	 * @param score This is a score of the player from the finished game;
	 * it must be positive integer.  
	 */
	public SaveScoreRequest(String nickname, int score) {
		super(requestPattern, requestHeader + " " + nickname + " " + score);
	}

}
