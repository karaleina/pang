package org.eiti.java.pangserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;

/**
 * This is class which enables to send best scores and
 * update them.
 * @author Karolina
 */
public class BestScoresManager {
	
	/** 
	 * This is a file, where best scores are kept.
	 */
	private final static String xmlBestScoresPath = "res/serverConfig/bestScores.xml";

	/**
	 * This is method which enables to send best scores to the client.
	 * @param outputStream This is stream where best scores are written
	 * @throws Exception
	 */
	public static synchronized void sendBestScores(DataOutputStream outputStream) throws Exception {
		FileSender.sendFile(
			new File(xmlBestScoresPath),
			outputStream);
	}
	
	/**
	 * This is method which enables to update list
	 * of best scores on the server.
	 * @param nickname Player nickname
	 * @param score Game score
	 * @throws Exception
	 */
	public static synchronized void updateBestScores(String nickname, int score) throws Exception {
		XMLBestScoresIO xmlBestScores = new XMLBestScoresIO(
			new FileInputStream(xmlBestScoresPath));
		xmlBestScores.update(nickname, score);
		FileOutputStream outputStream = new FileOutputStream(xmlBestScoresPath);
		xmlBestScores.save(outputStream);
		outputStream.close();
	}
	
}
