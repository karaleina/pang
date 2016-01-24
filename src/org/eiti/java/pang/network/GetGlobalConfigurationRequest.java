package org.eiti.java.pang.network;

import java.util.regex.Pattern;

/**
 * This request is used to get global configuration.
 * Example: GET_GLOBAL_CONFIGURATION
 * @author Karolina
 */
public class GetGlobalConfigurationRequest extends Request {
	
	/**
	 * This is a request header.
	 */
	private final static String requestHeader = "GET_GLOBAL_CONFIGURATION";

	/**
	 * This is a regex (regular expression)
	 * pattern for getting global configuration.
	 * It contains a request header.
	 */
	public final static Pattern requestPattern = Pattern.compile(requestHeader);
	
	/**
	 * It constructs a request from given content.
	 * It is used by the server.
	 */
	public GetGlobalConfigurationRequest(String content) {
		super(requestPattern, content);
	}
	
	/**
	 * It constructs a request.
	 * It is used by the client. 
	 */
	public GetGlobalConfigurationRequest() {
		super(requestPattern, requestHeader);
	}

}
