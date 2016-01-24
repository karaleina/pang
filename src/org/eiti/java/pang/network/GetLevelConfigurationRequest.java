package org.eiti.java.pang.network;

import java.util.regex.Pattern;

/**
 * This request is used to get configuration of particular level. 
 * Example: GET_LEVEL_CONFGIURATION 3
 * @author Karolina
 */
public class GetLevelConfigurationRequest extends Request {
	
	/**
	 * This is a request header.
	 */
	private final static String requestHeader = "GET_LEVEL_CONFIGURATION";

	/**
	 * This is a regex (regular expression)
	 * pattern for getting level configuration requests.
	 * It contains a request header and a level number (int).
	 */
	public final static Pattern requestPattern = Pattern.compile(requestHeader + " (\\d+)");
	
	/**
	 * It constructs a request from given content.
	 * It is used by the server.
	 */
	public GetLevelConfigurationRequest(String content) {
		super(requestPattern, content);
	}
	
	/**
	 * It constructs a request from given number.
	 * It is used by the client. 
	 */
	public GetLevelConfigurationRequest(int levelNumber) {
		super(requestPattern, requestHeader + " " + levelNumber);
	}

}
