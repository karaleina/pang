package org.eiti.java.pang.network;

import java.util.regex.Pattern;

public class GetBestScoresRequest extends Request {
	
	private final static String requestHeader = "GET_BEST_SCORES";

	public final static Pattern requestPattern = Pattern.compile(requestHeader);
	
	public GetBestScoresRequest(String content) {
		super(requestPattern, content);
	}
	
	public GetBestScoresRequest() {
		super(requestPattern, requestHeader);
	}

}
