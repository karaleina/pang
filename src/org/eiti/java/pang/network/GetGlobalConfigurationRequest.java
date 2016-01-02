package org.eiti.java.pang.network;

import java.util.regex.Pattern;

public class GetGlobalConfigurationRequest extends Request {
	
	private final static String requestHeader = "GET_GLOBAL_CONFIGURATION";

	public final static Pattern requestPattern = Pattern.compile(requestHeader);
	
	public GetGlobalConfigurationRequest(String content) {
		super(requestPattern, content);
	}
	
	public GetGlobalConfigurationRequest() {
		super(requestPattern, requestHeader);
	}

}
