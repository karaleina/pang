package org.eiti.java.pang.network;

import java.util.regex.Pattern;

/**
 * This request is used to check whether level of given number exists.
 * Example: CHECK_LEVEL_EXISTENCE 4 
 * @author Karolina
 */
public class CheckLevelExistenceRequest extends Request {

	/**
	 * This is a request header.
	 */
	private final static String requestHeader = "CHECK_LEVEL_EXISTENCE";

	/**
	 * This is a regex (regular expression)
	 * pattern for checking level existence requests.
	 * It contains a request header and a level number (int).
	 */
	public final static Pattern requestPattern = Pattern.compile(requestHeader + " (\\d+)");
	
	/**
	 * It constructs a request from given content.
	 * It is used by the server.
	 */
	public CheckLevelExistenceRequest(String content) {
		super(requestPattern, content);
	}
	
	/**
	 * It constructs a request from given number.
	 * It is used by the client. 
	 */
	public CheckLevelExistenceRequest(int levelNumber) {
		super(requestPattern, requestHeader + " " + levelNumber);
	}
	
}
