package org.eiti.java.pang.network;

import java.util.regex.Pattern;

public class GetLevelConfigurationRequest extends Request {
	
	private final static String requestHeader = "GET_LEVEL_CONFIGURATION";

	public final static Pattern requestPattern = Pattern.compile(requestHeader + " (\\d+)");
	
	public GetLevelConfigurationRequest(String content) {
		super(requestPattern, content);
	}
	
	public GetLevelConfigurationRequest(int levelNumber) {
		super(requestPattern, requestHeader + " " + levelNumber);
	}

}
