package org.eiti.java.pang.network;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Request {

	private final Pattern pattern;
	private final String content;
	
	public Request(Pattern pattern, String content) {
		this.pattern = pattern;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public List<String> getParameters() {
		Matcher matcher = pattern.matcher(content);
		if(matcher.matches()) {
			List<String> parameters = new ArrayList<String>();
			for(int i = 0; i < matcher.groupCount(); i++) {
				parameters.add(matcher.group(i + 1));
			}
			return parameters;
		} else {
			throw new RuntimeException("Invalid request content!");
		}
	}
}
