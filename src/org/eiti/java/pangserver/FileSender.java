package org.eiti.java.pangserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileSender {

	/**
	 * Sends file size (long = 8 bytes) and then the file content.
	 * 
	 * @param file
	 * @param outputStream
	 * @throws Exception
	 */
	public static void sendFile(File file, DataOutputStream outputStream) throws Exception {
		sendFileSize(file, outputStream);
		sendFileContent(file, outputStream);
		outputStream.flush();
	}
	
	private static void sendFileSize(File file, DataOutputStream outputStream) throws Exception {
		if(file.length() > (long) Integer.MAX_VALUE) {
			throw new RuntimeException("File is too big!");
		}
		outputStream.writeInt((int) file.length());
	}
	
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
