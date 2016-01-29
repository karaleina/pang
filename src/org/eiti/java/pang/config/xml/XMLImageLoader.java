package org.eiti.java.pang.config.xml;

import java.awt.Dimension;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * This class does not load image set itself, but a filepath and dimensions of each image in a given graphical theme.
 * The document should fit to following template:
 * <pre>
 * {@code
 *  <theme>
 *     <imagepaths>
 *         <background>res/themes/western/background.jpg</background>
 *         <heartImage>res/themes/western/heart.png</heartImage>
 *         <playerAvatarImage>res/themes/western/player.png</playerAvatarImage>
 *         <standardMissileImage>res/themes/western/old-bullet.png</standardMissileImage>
 *         <superMissileImage>res/themes/western/super-bullet.png</superMissileImage>
 *         <superWeaponImage>res/themes/western/mauser.png</superWeaponImage>
 *     </imagepaths>
 *     <dimensions>
 *         <heartWidth>60</heartWidth>
 *         <heartHeight>60</heartHeight>
 *         <playerAvatarWidth>60</playerAvatarWidth>
 *         <playerAvatarHeight>160</playerAvatarHeight>
 *         <standardMissileWidth>7</standardMissileWidth>
 *         <standardMissileHeight>20</standardMissileHeight>
 *         <superWeaponWidth>90</superWeaponWidth>
 *         <superWeaponHeight>60</superWeaponHeight>
 *         <superMissileWidth>7</superMissileWidth>
 *         <superMissileHeight>30</superMissileHeight>
 *     </dimensions>
 * </theme>
 * }
 * </pre>
 */
public class XMLImageLoader extends XMLParser{

	/**
	 *
	 * @param configurationFilePath This configuration file should be stored locally.
	 * @throws Exception is thrown when the configuration file is not accessible by given filepath.
     */
	public XMLImageLoader(String configurationFilePath) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		xmlDocument = builder.parse(configurationFilePath);

		xpath = XPathFactory.newInstance().newXPath();
	}

	/**
	 * This file should be sto
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getBackgroundPath() throws XPathExpressionException {
		return xpath.compile("//imagepaths/background").evaluate(xmlDocument);
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getHeartImagePath() throws XPathExpressionException{
		return xpath.compile("//imagepaths/heartImage").evaluate(xmlDocument);	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getPlayerAvatarImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/playerAvatarImage").evaluate(xmlDocument);
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getStandardMissileImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/standardMissileImage").evaluate(xmlDocument);
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getSuperMissileImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/superMissileImage").evaluate(xmlDocument);
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getSuperWeaponImage() throws XPathExpressionException {
		return xpath.compile("//imagepaths/superWeaponImage").evaluate(xmlDocument);
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public Dimension getPlayerAvatarDim() throws XPathExpressionException {
		int PAWidth  = Integer.parseInt(xpath.compile("//dimensions/playerAvatarWidth").evaluate(xmlDocument));
		int PAHeight = Integer.parseInt(xpath.compile("//dimensions/playerAvatarHeight").evaluate(xmlDocument));
		return new Dimension(PAWidth, PAHeight);
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getPlayerAvatarWidth() throws  XPathExpressionException {
		return Integer.parseInt(xpath.compile("//dimensions/playerAvatarWidth").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getPlayerAvatarHeight() throws  XPathExpressionException {
		return Integer.parseInt(xpath.compile("//dimensions/playerAvatarHeight").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperWeaponWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superWeaponWidth").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperWeaponHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superWeaponHeight").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getHeartWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/heartWidth").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getHeartHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/heartHeight").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getStandardMissileWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/standardMissileWidth").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getStandardMissileHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/standardMissileHeight").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperMissileWidth() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superMissileWidth").evaluate(xmlDocument));
	}

	/**
	 *
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getSuperMissileHeight() throws XPathExpressionException{
		return  Integer.parseInt(xpath.compile("//dimensions/superMissileHeight").evaluate(xmlDocument));
	}
}
