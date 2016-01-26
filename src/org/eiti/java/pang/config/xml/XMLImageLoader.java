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

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getBackgroundPath() throws XPathExpressionException {
		return xpath.compile("//imagepaths/background").evaluate(xmlDocument);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getHeartImagePath() throws XPathExpressionException{
		return xpath.compile("//imagepaths/heartImage").evaluate(xmlDocument);	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getPlayerAvatarImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/playerAvatarImage").evaluate(xmlDocument);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getStandardMissileImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/standardMissileImage").evaluate(xmlDocument);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getSuperMissileImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/superMissileImage").evaluate(xmlDocument);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getSuperWeaponImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/superWeaponImage").evaluate(xmlDocument);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public Dimension getPlayerAvatarDim() throws XPathExpressionException {
		int PAWidth  = Integer.parseInt(xpath.compile("//dimensions/playerAvatarWidth").evaluate(xmlDocument));
		int PAHeight = Integer.parseInt(xpath.compile("//dimensions/playerAvatarHeight").evaluate(xmlDocument));
		return new Dimension(PAWidth, PAHeight);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getPlayerAvatarWidth() throws  XPathExpressionException {
		return Integer.parseInt(xpath.compile("//dimensions/playerAvatarWidth").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getPlayerAvatarHeight() throws  XPathExpressionException {
		return Integer.parseInt(xpath.compile("//dimensions/playerAvatarHeight").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperWeaponWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superWeaponWidth").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperWeaponHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superWeaponHeight").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getHeartWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/heartWidth").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getHeartHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/heartHeight").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getStandardMissileWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/standardMissileWidth").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getStandardMissileHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/standardMissileHeight").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperMissileWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superMissileWidth").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperMissileHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superMissileHeight").evaluate(xmlDocument));
	}
}
