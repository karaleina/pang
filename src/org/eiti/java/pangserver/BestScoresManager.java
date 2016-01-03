package org.eiti.java.pangserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;

public class BestScoresManager {
	
	private final static String xmlBestScoresPath = "res/serverConfig/bestScoresExample.xml";

	public static synchronized void sendBestScores(DataOutputStream outputStream) throws Exception {
		FileSender.sendFile(
			new File(xmlBestScoresPath),
			outputStream);
	}
	
	public static synchronized void updateBestScores(String nickname, int score) throws Exception {
		XMLBestScoresIO xmlBestScores = new XMLBestScoresIO(
			new FileInputStream(xmlBestScoresPath));
		xmlBestScores.update(nickname, score);
		FileOutputStream outputStream = new FileOutputStream(xmlBestScoresPath);
		xmlBestScores.save(outputStream);
		outputStream.close();
	}
	
}
