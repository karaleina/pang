package org.eiti.java.pang.config;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.io.FileInputStream;

/**
 * Created by Stefan Hennel on 26.11.15.
 */
public class XMLGlobalConfiguration extends XMLParser {



    public XMLGlobalConfiguration(String configurationFilePath) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmlDocument = builder.parse(new FileInputStream(configurationFilePath));

        xpath = XPathFactory.newInstance().newXPath();
    }

    public String getTitle() throws XPathExpressionException {
        return xpath.compile("//title").evaluate(xmlDocument);
    }

    public Dimension getGameWindowSize() throws XPathExpressionException {
        int gameWindowWidth  = (int) xpath.compile("//gameWindowSize/width").evaluate(xmlDocument, XPathConstants.NUMBER);
        int gameWindowHeight = (int) xpath.compile("//gameWindowSize/height").evaluate(xmlDocument, XPathConstants.NUMBER);
        return new Dimension(gameWindowWidth, gameWindowHeight);
    }

     public int getLives() throws XPathExpressionException {
         return (int) (xpath.compile("//lives").evaluate(xmlDocument, XPathConstants.NUMBER));
     }
}
