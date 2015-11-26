package org.eiti.java.pang.game;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Stefan Hennel on 26.11.15.
 */
public class XMLGlobalConfiguration extends XMLParser {

    public String getTitle(){
        Node titleNode = findChildByName(root, "title");
        String title = titleNode.getTextContent();
        return title;
    }

    public XMLGlobalConfiguration(File configurationFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlDocument = builder.parse(new FileInputStream(configurationFile));
        root = xmlDocument.getDocumentElement();
    }

    public Dimension getGameWindowSize() {
        Node gameWindowSize = findChildByName(root, "gameWindowSize");
        int gameWindowWidth = Integer.parseInt(findChildByName(gameWindowSize, "width").getTextContent());
        int gameWindowHeight = Integer.parseInt(findChildByName(gameWindowSize, "height").getTextContent());
        return new Dimension(gameWindowWidth, gameWindowHeight);
    }

     public int getLives(){
         Node livesNode = findChildByName(root, "lives");
         int lives = Integer.parseInt(livesNode.getTextContent());
         return lives;
     }
}
