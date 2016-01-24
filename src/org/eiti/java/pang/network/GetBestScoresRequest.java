package org.eiti.java.pang.network;

import java.util.regex.Pattern;

/**
 * This request is used to get best scores list.
 * Example: GET_BEST_SCORES 
 * @author Karolina
 */
public class GetBestScoresRequest extends Request {
	
	/**
	 * This is a request header.
	 */
	private final static String requestHeader = "GET_BEST_SCORES";

	/**
	 * This is a regex (regular expression)
	 * pattern for getting best scores list.
	 * It contains a request header.
	 */
	public final static Pattern requestPattern = Pattern.compile(requestHeader);
	
	/**
	 * It constructs a request from given content.
	 * It is used by the server.
	 */
	public GetBestScoresRequest(String content) {
		super(requestPattern, content);
	}
	
	/**
	 * It constructs a request.
	 * It is used by the client. 
	 */
	public GetBestScoresRequest() {
		super(requestPattern, requestHeader);
	}

}
