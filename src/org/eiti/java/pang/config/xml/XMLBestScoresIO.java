package org.eiti.java.pang.config.xml;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Created by Stefan Hennel on 13.12.15.
 */
public class XMLBestScoresIO extends XMLParser {

    private int maxEntryNumber;        //how many entries are expected (upper limit)

    public XMLBestScoresIO(InputStream inputStream) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        xmlDocument = builder.parse(inputStream);

        xpath = XPathFactory.newInstance().newXPath();

        maxEntryNumber = Short.parseShort(xpath.compile("//maxEntryNumber").evaluate(xmlDocument));
        //będzie mialo duże znaczenie przy zapisie!
    }

    public int getMaxEntryNumber() {return maxEntryNumber;}

    public ArrayList<String> getBestPlayers() throws XPathExpressionException {
        ArrayList<String> bestPlayers = new ArrayList<>();
        int entryNumber = xmlDocument.getElementsByTagName("player").getLength();

        for (int i = 1; i <= entryNumber; i++) {
            String nicknameXPath  = "//players/player[" + i + "]/nickname";
            String nickname = xpath.compile(nicknameXPath).evaluate(xmlDocument);
            bestPlayers.add(nickname);
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

    public void update(String nickname, int score) throws XPathExpressionException {
        int position;       //position of new score in the record table

        int entryNumber = xmlDocument.getElementsByTagName("player").getLength();
        Node players = (Node) xpath.compile("//players").evaluate(xmlDocument, XPathConstants.NODE);

        //Note: xmlDocument acts here as a creator, not a representation of the *.xml file
        Element newPlayer   = xmlDocument.createElement("player");
        Element newNickname = xmlDocument.createElement("nickname");
        newNickname.setNodeValue(nickname);
        Element newScore    = xmlDocument.createElement("score");
        newScore.setNodeValue(String.valueOf(score));
        newPlayer.appendChild(newNickname);
        newPlayer.appendChild(newScore);

        ArrayList<Integer> oldBestScores = getBestScores();
        for (int i = 0; i < oldBestScores.size(); i++) {
            if (oldBestScores.get(i) < score){
                position = i;
                break;
            } else
                position = i + 1;
        }

        if (entryNumber < maxEntryNumber) {

        }
    }


}
