package org.eiti.java.pang.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * This class represents a connection between client and server.
 * @author Karolina
 */
public class ServerConnection {
	
	/**
	 * This is a network socket connected to a server.
	 */
	private final Socket socket;
	
	/**
	 * This constructor creates a connection between client and serwer.
	 * @param host It is an address of the server
	 * @param portNumber It is the port number
	 * @throws IOException
	 */
	public ServerConnection(String host, int portNumber) throws IOException {
		socket = new Socket(host, portNumber);
		System.out.println("connected!");
	}
	
	/**
	 * This is a method which enables to send requests. 
	 * Strings are encoded in utf-8.
	 * @param request It is the request to send
	 * @throws IOException
	 */
	public void sendRequest(Request request) throws IOException {
		String requestContent = request.getContent() + "\n";
		socket.getOutputStream().write(requestContent.getBytes(Charset.forName("utf-8")));
		socket.getOutputStream().flush();
	}
	
	/**
	 * It enables to get input stream from the socket.
	 */
	public InputStream getInputStream() {
		try {
			return socket.getInputStream();
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	/**
	 * It enables to close the connection.
	 * @throws IOException
	 */
	public void close() throws IOException {
		socket.close();
	}

}
