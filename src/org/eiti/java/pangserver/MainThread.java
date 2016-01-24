package org.eiti.java.pangserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

/**
 *  This is the main thread which accepts connections
 *  from clients.
 * @author Karolina
 */
public class MainThread extends Thread {
	
	/**
	 * This is a port number.
	 */
	private int portNumber;
	
	/**
	 * This is a list of active client threads.
	 */
	private List<ClientThread> clientThreads = new LinkedList<ClientThread>();
	
	/**
	 * This constructs the main thread on the server.
	 */
	public MainThread(int portNumber) {
		this.portNumber = portNumber;
	}
	
	/**
	 * In run method there is created new thread for new connected client.
	 */
	@Override
	public void run() {
		try {
			ServerSocket socket = new ServerSocket(portNumber);
			
			// this allows to interrupt the thread
			socket.setSoTimeout(500); 
			
			while(!isInterrupted()) {
				try {
					Socket clientSocket = socket.accept();
					ClientThread thread = new ClientThread(clientSocket);
					clientThreads.add(thread);
					thread.start();
				} catch(SocketTimeoutException exc) {}
			}
			socket.close();
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
		cleanupClientThreads();
	}
	
	/**
	 * This method interrupts threads of all clients.
	 */
	private void cleanupClientThreads() {
		for(ClientThread clientThread : clientThreads) {
			if(clientThread.isRunning()) {
				clientThread.interrupt();
				try {
					clientThread.join();
				} catch(InterruptedException exc) {}
			}
		}
	}

}
