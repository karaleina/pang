package org.eiti.java.pang.config;

import org.eiti.java.pang.global.GlobalConstantsLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Stefan Hennel on 13.12.15.
 */
public class XMLBestScoresIO extends XMLParser {

    private short maxEntryNumber;        //how many entries are expected (upper limit)

    public XMLBestScoresIO() throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        xmlDocument = builder.parse(new FileInputStream(GlobalConstantsLoader.bestScoresPath));

        xpath = XPathFactory.newInstance().newXPath();

        maxEntryNumber = Short.parseShort(xpath.compile("//maxEntryNumber").evaluate(xmlDocument));
        //będzie mialo duże znaczenie przy zapisie!
    }

    public short getMaxEntryNumber() {return maxEntryNumber;}

    public ArrayList<String> getBestPlayers() throws XPathExpressionException {
        ArrayList<String> bestPlayers = new ArrayList<>();
        int entryNumber = xmlDocument.getElementsByTagName("player").getLength();

        for (int i = 1; i <= entryNumber; i++) {
            String nameXPath  = "//players/player[" + i + "]/name";
            String name = xpath.compile(nameXPath).evaluate(xmlDocument);
            bestPlayers.add(name);
        }
        return bestPlayers;
    }

    public ArrayList<Integer> getBestScores() throws XPathExpressionException {
        ArrayList<Integer> bestScores = new ArrayList<>();
        int entryNumber = xmlDocument.getElementsByTagName("player").getLength();

        for (int i = 1; i <= entryNumber; i++) {
            String scoreXPath = "//players/player[" + i + "]/score";
            int score = Integer.parseInt(xpath.compile(scoreXPath).evaluate(xmlDocument));
            bestScores.add(score);
        }
        return bestScores;
    }
    //Wyświetamy "jak jest" ale za to zapisywać trzeba z sensem
}
