package org.eiti.java.pang.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class ServerConnection {
	
	private final Socket socket;
	
	public ServerConnection(String host, int portNumber) throws IOException {
		socket = new Socket(host, portNumber);
		System.out.println("connected!");
	}
	
	public void sendRequest(Request request) throws IOException {
		String requestContent = request.getContent() + "\n";
		socket.getOutputStream().write(requestContent.getBytes(Charset.forName("utf-8")));
		socket.getOutputStream().flush();
	}
	
	public InputStream getInputStream() {
		try {
			return socket.getInputStream();
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	public void close() throws IOException {
		socket.close();
	}

}
