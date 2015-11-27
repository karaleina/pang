package org.eiti.java.pang.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;

import org.w3c.dom.Node;

/**
 * Created by Stefan Hennel on 27.11.15.
 */
public class XMLImagePaths extends XMLParser{

	public XMLImagePaths(File configurationFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(new FileInputStream(configurationFile));
		root = xmlDocument.getDocumentElement();
	}

	public String getBackgroundPath(){
		Node backgroundNode = findChildByName(root, "backgroundImage");
		String backgroundPath = backgroundNode.getTextContent();
		return backgroundPath;
	}

	public String getHeartImagePath(){
		Node heartImageNode = findChildByName(root, "heartImage");
		String heartImagePath = heartImageNode.getTextContent();
		return heartImagePath;
	}

	public String getPlayerAvatarImage(){
		Node playerAvatarImageNode = findChildByName(root, "playerAvatarImage");
		String playerAvatarImagePath = playerAvatarImageNode.getTextContent();
		return playerAvatarImagePath;
	}

	//TODO reszta ścieżek


}
