package org.eiti.java.pang.config.xml;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Stefan Hennel on 26.11.15.
 */
public class XMLGlobalConfiguration extends XMLParser {

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

    public String getBestScoresPath() throws XPathExpressionException {
        return xpath.compile("//bestScoresPath").evaluate(xmlDocument);
    }
}
