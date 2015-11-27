package org.eiti.java.pang.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;

import org.w3c.dom.Node;

/**
 * Created by Stefan Hennel on 27.11.15.
 */
public class XMLImagePaths extends XMLParser{

	public XMLImagePaths(String configurationFilePath) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(new FileInputStream(configurationFilePath));
		root = xmlDocument.getDocumentElement();
	}

	public String getBackgroundPath(){
		Node backgroundNode = findChildByName(root, "background");
		return backgroundNode.getTextContent();
	}

	public String getHeartImagePath(){
		Node heartImageNode = findChildByName(root, "heartImage");
		return heartImageNode.getTextContent();
	}

	public String getPlayerAvatarImage(){
		Node playerAvatarImageNode = findChildByName(root, "playerAvatarImage");
		return playerAvatarImageNode.getTextContent();
	}

	public String getStandardMissileImage(){
		Node standardMissileImageNode = findChildByName(root, "standardMissileImage");
		return standardMissileImageNode.getTextContent();
	}

	public String getSuperMissileImage(){
		Node superMissileImageNode = findChildByName(root, "superMissileImage");
		return superMissileImageNode.getTextContent();
	}

	public String getSuperWeaponImage(){
		Node superWeaponImage = findChildByName(root, "superWeaponImage");
		return superWeaponImage.getTextContent();
	}

}
