package org.eiti.java.pang.config.xml;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * This class reads global configuration of the game from an XML document. The document should fit to following template:
 * <global>
 *     <title>Pang</title>
 *     <gameWindowSize>
 *     <width>650</width>
 *         <height>400</height>
 *         </gameWindowSize>
 *     <lives>5</lives>
 *     <!-- pixels / ms^2 -->
 *     <gravity>0.0001</gravity>
 *     <!-- pixels / ms -->
 *     <playerVelocity>0.16</playerVelocity>
 *     <!-- milliseconds -->
 *     <minTimeBetweenShots>500</minTimeBetweenShots>
 * </global>
 *
 */
public class XMLGlobalConfiguration extends XMLParser {
    /**
     *
     * @param inputStream The input stream of bytes consist of an XML file,
     *                    which either should lie under "res/config/global.xml" or should be sent by a server.
     * @throws Exception An exception is thrown when file does not exists or is not correct in a sense of XML syntax
     */
    public XMLGlobalConfiguration(InputStream inputStream) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmlDocument = builder.parse(inputStream);
        xpath = XPathFactory.newInstance().newXPath();
    }

    public String getTitle() throws XPathExpressionException {
        return xpath.compile("//title").evaluate(xmlDocument);
    }

    public Dimension getGameWindowSize() throws XPathExpressionException {
        int gameWindowWidth  = Integer.parseInt(xpath.compile("//gameWindowSize/width").evaluate(xmlDocument));
        int gameWindowHeight = Integer.parseInt(xpath.compile("//gameWindowSize/height").evaluate(xmlDocument));
        return new Dimension(gameWindowWidth, gameWindowHeight);
    }

     public int getLives() throws XPathExpressionException {
         return Integer.parseInt((xpath.compile("//lives").evaluate(xmlDocument)));
     }

    public double getGravity() throws XPathExpressionException {
        return Double.parseDouble(xpath.compile("//gravity").evaluate(xmlDocument));
    }

	public double getPlayerVelocity() throws XPathExpressionException {
		return Double.parseDouble(xpath.compile("//playerVelocity").evaluate(xmlDocument));
	}

	public long getMinTimeBetweenShots() throws XPathExpressionException {
		return Long.parseLong(xpath.compile("//minTimeBetweenShots").evaluate(xmlDocument));
	}
}
