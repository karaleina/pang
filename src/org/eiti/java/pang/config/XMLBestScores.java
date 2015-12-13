package org.eiti.java.pang.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Stefan Hennel on 13.12.15.
 */
public class XMLBestScores extends XMLParser {

    private short maxEntyNumber;        //how many entries are expected (upper imit)
    private SortedMap<String, Integer> bestScores;

    public XMLBestScores(String configurationFilePath) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmlDocument = builder.parse(new FileInputStream(configurationFilePath));

        xpath = XPathFactory.newInstance().newXPath();

        maxEntyNumber = Short.parseShort(xpath.compile("//maxEntryNumber").evaluate(xmlDocument));
    }



    public SortedMap<String, Integer> getBestScores() {
        for (int i = 0; i < maxEntyNumber; i++) {

        }
        return bestScores;
    }
}
