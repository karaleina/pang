package org.eiti.java.pangserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * This is class which enables to send files to the client.
 * @author Karolina
 */
public class FileSender {

	/**
	 * Sends:
	 * - file size (int = 4 bytes)
	 * - file content
	 * to given output stream.
	 * @throws Exception
	 */
	public static void sendFile(File file, DataOutputStream outputStream) throws Exception {
		sendFileSize(file, outputStream);
		sendFileContent(file, outputStream);
		outputStream.flush();
	}
	
	/**
	 * Sends the size of given file to the stream.
	 * @throws Exception
	 */
	private static void sendFileSize(File file, DataOutputStream outputStream) throws Exception {
		if(file.length() > (long) Integer.MAX_VALUE) {
			throw new RuntimeException("File is too big!");
		}
		outputStream.writeInt((int) file.length());
	}
	
	/**
	 * Sends the content of given file to the stream. 
	 * @throws Exception
	 */
	private static void sendFileContent(File file, DataOutputStream outputStream) throws Exception {
		FileInputStream inputStream = new FileInputStream(file);
		
		final int bufferSize = 4096;
		byte[] buffer = new byte[bufferSize];
		
		int bytesRead = 0;
		
		while((bytesRead = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, bytesRead);
		}
		
		inputStream.close();
	}
	
}
