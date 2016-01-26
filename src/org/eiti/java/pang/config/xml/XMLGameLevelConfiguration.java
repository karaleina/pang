package org.eiti.java.pang.config.xml;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.ExtraObjectType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class reads level configuration from an XML document. The document should fit to following template:
 * 	<level>
 *		<number>1</number>
 *		<time>120</time>
 *		<gameWorldSize>
 *			<width>800</width>
 *			<height>450</height>
 *		</gameWorldSize>
 *		<player>
 *			<!-- left, center, right -->
 *			<position>left</position>
 *		</player>
 *		<balls>
 *			<ball>
 *				<position>
 *					<x>20</x>
 *					 <y>100</y>
 *		 		</position>
 *		 		<level>2</level>
 *				 <speed>
 *				 <x>0</x>
 *					 <y>-5</y>
 *				 </speed>
 *		 	</ball>
 *		 </balls>
 *		 <extraObjects>
 *		 <!-- probabilities of appearance per minute -->
 *		 	<heart>1.0</heart>
 *			 <superWeapon>1.0</superWeapon>
 *		 </extraObjects>
 *		 </level>
 */

public class XMLGameLevelConfiguration extends XMLParser {

	public XMLGameLevelConfiguration(InputStream inputStream) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDocument = builder.parse(inputStream);
		xpath = XPathFactory.newInstance().newXPath();
	}


	/**
	 * @return Dimensions of modeled "world of game". This parameter can vary from level to level.
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public Dimension getGameWorldSize() throws XPathExpressionException {

		int gameWorldWidth  = Integer.parseInt(xpath.compile("//gameWorldSize/width").evaluate(xmlDocument));
		int gameWorldHeight = Integer.parseInt(xpath.compile("//gameWorldSize/height").evaluate(xmlDocument));

		return new Dimension(gameWorldWidth, gameWorldHeight);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public List<Ball> getBalls() throws XPathExpressionException {

        List<Ball> ballList = new ArrayList<>();
        int ballsNumber = xmlDocument.getElementsByTagName("ball").getLength();

		for (int i = 1; i <= ballsNumber; i++) {	//foreach loop does not work for NodeList interface
            String ballPath = "//balls/ball[" + i + "]";

            int ballPositionX = Integer.parseInt(xpath.compile(ballPath + "/position/x").evaluate(xmlDocument));
			int ballPositionY = Integer.parseInt(xpath.compile(ballPath + "/position/y").evaluate(xmlDocument));

			double ballSpeedX = Double.parseDouble(xpath.compile(ballPath + "/speed/x").evaluate(xmlDocument));
			double ballSpeedY = Double.parseDouble(xpath.compile(ballPath + "/speed/y").evaluate(xmlDocument));
			
			int ballLevel = Integer.parseInt(xpath.compile(ballPath + "/level").evaluate(xmlDocument));
			
			ballList.add(
				new Ball(
					new Point2D.Double(ballPositionX, ballPositionY),
					ballLevel,
					new double[] { ballSpeedX, ballSpeedY },
					this.getGameWorldSize()));
		}

        return ballList;
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public String getPlayerAvatarPosition() throws XPathExpressionException {
		return xpath.compile("//player/position").evaluate(xmlDocument);
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public int getTimeForLevel() throws XPathExpressionException {

		return Integer.parseInt(xpath.compile("//time").evaluate(xmlDocument));
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	public Map<ExtraObjectType, Double> getExtraObjectsProbabilities() throws XPathExpressionException {
		Map<ExtraObjectType, Double> probabilities = new HashMap<ExtraObjectType, Double>();

        int extraObjectsNumber = getChildrenCountForUniqueTagName(xmlDocument, "extraObjects");
        for (int i = 1; i <= extraObjectsNumber; i++) {	//foreach loop does not work for NodeList interface
			String i_thExtraObjectPath = "//extraObjects/*[" + i + "]";

            double probability = Double.parseDouble(xpath.compile(i_thExtraObjectPath).evaluate(xmlDocument));
			if(probability < 0.0 || probability > 1.0) {
				throw new RuntimeException("Probability " + probability + " out of range [0, 1]!");
			}
            String extraObjectName = xpath.compile("name(" + i_thExtraObjectPath +")").evaluate(xmlDocument);
			probabilities.put(
				ExtraObjectType.valueOf(extraObjectName),
				probability);
		}
		
		return probabilities;
	}

	/**
	 * @return
	 * @throws XPathExpressionException is thrown when the correct entry is not found in the XML document.
	 */
	private int getChildrenCountForUniqueTagName(Document document, String tagName) {
		int counter = 0;
		NodeList elementsWithTagName = document.getElementsByTagName(tagName);
		assert(elementsWithTagName.getLength() == 1);
		NodeList children = elementsWithTagName.item(0).getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE) {
				counter++;
			}
		}
		return counter;
	}

}
