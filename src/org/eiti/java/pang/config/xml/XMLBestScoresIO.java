package org.eiti.java.pang.config.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eiti.java.pang.game.HighScoreEntry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class reads and writes best scores as an XML document entries. The document should fit to following template:
 * <?xml version="1.0" encoding="utf-8"?>
 * <bestScores>
 *  <!-- max. number of saved scores-->
 *   <maxEntryNumber>10</maxEntryNumber>
 *   <players>
 *   <player>
 *       <nickname>Player 10</nickname>
 *   <score>10</score></player>
 *   <player>
 *       <nickname>Player 9</nickname>
 *       <score>9</score>
 *   </player>
 *   <player>
 *       <nickname>Player 8</nickname>
 *       <score>8</score>
 *   </player>
 *   <!-- etc. -->
 * </bestScores>
 *
 */
public class XMLBestScoresIO extends XMLParser {

    private int maxEntryNumber;        //how many entries are expected (upper limit)

    public XMLBestScoresIO(InputStream inputStream) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        xmlDocument = builder.parse(inputStream);
        xpath = XPathFactory.newInstance().newXPath();
        maxEntryNumber = Integer.parseInt(xpath.compile("//maxEntryNumber").evaluate(xmlDocument));
    }

    public ArrayList<HighScoreEntry> getEntries() throws XPathExpressionException {
        ArrayList<HighScoreEntry> entries = new ArrayList<>();
        int entryNumber = xmlDocument.getElementsByTagName("player").getLength();

        for (int i = 1; i <= entryNumber; i++) {
            String nicknameXPath  = "//players/player[" + i + "]/nickname";
            String nickname = xpath.compile(nicknameXPath).evaluate(xmlDocument);
            String scoreXPath = "//players/player[" + i + "]/score";
            int score = Integer.parseInt(xpath.compile(scoreXPath).evaluate(xmlDocument));
            entries.add(new HighScoreEntry(nickname, score));
        }
        return entries;
    }

    public void update(String nickname, int score) throws XPathExpressionException {
        List<HighScoreEntry> oldBestEntries = getEntries();
        updateBestScores(oldBestEntries, nickname, score);
        updateXML(oldBestEntries);
    }

    private void updateBestScores(List<HighScoreEntry> entries, String nickname, int score) {
        int insertBeforeIndex = findPlaceForScore(entries, score);
        if(insertBeforeIndex < maxEntryNumber) {
            entries.add(insertBeforeIndex, new HighScoreEntry(nickname, score));
        }
        if(entries.size() > maxEntryNumber) {
            entries.remove(entries.size() - 1);
        }
    }

    private int findPlaceForScore(List<HighScoreEntry> entries, int score) {
        int i = 0;
        while(i < entries.size() && score <= entries.get(i).getScore()) {
            i++;
        }
        return i;
    }

    private void updateXML(List<HighScoreEntry> entries) throws XPathExpressionException {
        Node players = xmlDocument.getElementsByTagName("players").item(0);
        removeChildNodes(players);
        for (HighScoreEntry entry : entries) {
            Element highScoreXMLElement = createXMLHighScoreEntry(entry);
            players.appendChild(highScoreXMLElement);
        }
    }

    private void removeChildNodes(Node node) {
        NodeList children = node.getChildNodes();
        int childrenToRemove = children.getLength();
        for (int i = 0; i < childrenToRemove; i++) {
            node.removeChild(children.item(0));
        }
    }

    private Element createXMLHighScoreEntry(HighScoreEntry entry) {
        Element newPlayer = xmlDocument.createElement("player");
        Element newNickname = xmlDocument.createElement("nickname");
        Element newScore = xmlDocument.createElement("score");

        newNickname.setTextContent(entry.getNickname());
        newScore.setTextContent(String.valueOf(entry.getScore()));

        newPlayer.appendChild(newNickname);
        newPlayer.appendChild(newScore);

        return newPlayer;
    }

    public void save(OutputStream outputStream) throws Exception {
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(xmlDocument);
        StreamResult result =  new StreamResult(outputStream);
        transformer.transform(source, result);
        outputStream.flush();
    }

}
