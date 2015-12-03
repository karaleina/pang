package org.eiti.java.pang.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;

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
		return xpath.compile("//background").evaluate(xmlDocument);
	}

	public String getHeartImagePath() throws XPathExpressionException{
		return xpath.compile("//heartImage").evaluate(xmlDocument);	}

	public String getPlayerAvatarImage() throws XPathExpressionException {
		return xpath.compile("//playerAvatarImage").evaluate(xmlDocument);
	}

	public String getStandardMissileImage() throws XPathExpressionException{
		return xpath.compile("//standardMissileImage").evaluate(xmlDocument);
	}

	public String getSuperMissileImage() throws XPathExpressionException{
		return xpath.compile("//superMissileImage").evaluate(xmlDocument);
	}

	public String getSuperWeaponImage() throws XPathExpressionException{
		return xpath.compile("//superWeaponImage").evaluate(xmlDocument);
	}

}
