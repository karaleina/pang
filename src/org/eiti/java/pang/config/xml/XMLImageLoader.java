package org.eiti.java.pang.config.xml;

import java.awt.Dimension;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Stefan Hennel on 27.11.15.
 */
public class XMLImageLoader extends XMLParser{

	public XMLImageLoader(String configurationFilePath) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		xmlDocument = builder.parse(new FileInputStream(configurationFilePath));

		xpath = XPathFactory.newInstance().newXPath();
	}

	public String getBackgroundPath() throws XPathExpressionException {
		return xpath.compile("//imagepaths/background").evaluate(xmlDocument);
	}

	public String getHeartImagePath() throws XPathExpressionException{
		return xpath.compile("//imagepaths/heartImage").evaluate(xmlDocument);	}

	public String getPlayerAvatarImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/playerAvatarImage").evaluate(xmlDocument);
	}

	public String getStandardMissileImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/standardMissileImage").evaluate(xmlDocument);
	}

	public String getSuperMissileImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/superMissileImage").evaluate(xmlDocument);
	}

	public String getSuperWeaponImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/superWeaponImage").evaluate(xmlDocument);
	}

	public Dimension getPlayerAvatarDim() throws XPathExpressionException {
		int PAWidth  = Integer.parseInt(xpath.compile("//dimensions/playerAvatarWidth").evaluate(xmlDocument));
		int PAHeight = Integer.parseInt(xpath.compile("//dimensions/playerAvatarHeight").evaluate(xmlDocument));
		return new Dimension(PAWidth, PAHeight);
	}

	public int getPlayerAvatarWidth() throws  XPathExpressionException {
		return Integer.parseInt(xpath.compile("//dimensions/playerAvatarWidth").evaluate(xmlDocument));
	}

	public int getPlayerAvatarHeight() throws  XPathExpressionException {
		return Integer.parseInt(xpath.compile("//dimensions/playerAvatarHeight").evaluate(xmlDocument));
	}

	public int getSuperWeaponWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superWeaponWidth").evaluate(xmlDocument));
	}

	public int getSuperWeaponHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superWeaponHeight").evaluate(xmlDocument));
	}



}
