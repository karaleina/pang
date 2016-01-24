package org.eiti.java.pang.network;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a request which is sent
 * by the client and received by the server.
 * @author Karolina
 */
public abstract class Request {

	/**
	 * This is a regex (regular expression)
	 * pattern used to differentiate requests. 
	 */
	private final Pattern pattern;
	
	/**
	 * This is request's content.
	 */
	private final String content;
	
	/**
	 * This constructs a request from given content and regex pattern.
	 */
	public Request(Pattern pattern, String content) {
		this.pattern = pattern;
		this.content = content;
	}
	
	/**
	 * This method enables to get request's content. 
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * This method extracts parameters from request content.
	 * @return The list of parameters represented as strings.
	 */
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
