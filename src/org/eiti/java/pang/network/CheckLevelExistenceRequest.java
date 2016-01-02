package org.eiti.java.pang.network;

import java.util.regex.Pattern;

public class CheckLevelExistenceRequest extends Request {

	private final static String requestHeader = "CHECK_LEVEL_EXISTENCE";

	public final static Pattern requestPattern = Pattern.compile(requestHeader + " (\\d+)");
	
	public CheckLevelExistenceRequest(String content) {
		super(requestPattern, content);
	}
	
	public CheckLevelExistenceRequest(int levelNumber) {
		super(requestPattern, requestHeader + " " + levelNumber);
	}
	
}
