package org.eiti.java.pang.network;

import java.util.regex.Pattern;

public class SaveScoreRequest extends Request {
	
	private final static String requestHeader = "SAVE_SCORE";
	
	public final static Pattern requestPattern = Pattern.compile(requestHeader + " (.+) (\\d+)");

	public SaveScoreRequest(String content) {
		super(requestPattern, content);
	}
	
	public SaveScoreRequest(String nickname, int score) {
		super(requestPattern, requestHeader + " " + nickname + " " + score);
	}

}
