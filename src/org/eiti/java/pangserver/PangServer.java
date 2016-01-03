package org.eiti.java.pangserver;

public class PangServer {
	
	private MainThread serverThread;
	
	private int portNumber = 0;
	
	public boolean isRunning() {
		return serverThread != null;
	}
	
	public int getPortNumber() {
		return portNumber;
	}
	
	public void start(int portNumber) {
		if(serverThread != null) {
			throw new RuntimeException("The server is already running!");
		} else {
			serverThread = new MainThread(portNumber);
			serverThread.start();
			this.portNumber = portNumber;
		}
	}
	
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
