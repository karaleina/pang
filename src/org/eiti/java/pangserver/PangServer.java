package org.eiti.java.pangserver;


/**
 * This represents pang server.
 * @author Karolina
 */
public class PangServer {
	
	/**
	 * This is the main thread which accepts connections
	 * from clients.
	 */
	private MainThread serverThread;
	
	/**
	 * This is port number.
	 */
	private int portNumber = 0;
	
	/**
	 * This is method which enables to check 
	 * if the server is running.
	 */
	public boolean isRunning() {
		return serverThread != null;
	}
	
	/**
	 * This returns port number.
	 */
	public int getPortNumber() {
		return portNumber;
	}
	
	/**
	 * This starts the server at given port number.
	 */
	public void start(int portNumber) {
		if(serverThread != null) {
			throw new RuntimeException("The server is already running!");
		} else {
			serverThread = new MainThread(portNumber);
			serverThread.start();
			this.portNumber = portNumber;
		}
	}
	
	/**
	 * This stops the server.
	 */
	public void stop() {
		if(serverThread != null) {
			serverThread.interrupt();
			try {
				serverThread.join();
			} catch(InterruptedException exc) {}
			serverThread = null;
			portNumber = 0;
		}
	}

}
