package org.eiti.java.pangserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

public class MainThread extends Thread {
	
	private int portNumber;
	
	private List<ClientThread> clientThreads = new LinkedList<ClientThread>();
	
	public MainThread(int portNumber) {
		this.portNumber = portNumber;
	}
	
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
