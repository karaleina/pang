package org.eiti.java.pang.config;

import java.awt.Dimension;
import java.awt.Point;

import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Moduł wczytujący pik XML opisujący poziom. Składnia według wzoru:
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

	public XMLGameLevelConfiguration(String configurationFilePath) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDocument = builder.parse(new FileInputStream(configurationFilePath));

		xpath = XPathFactory.newInstance().newXPath();

		root = xmlDocument.getDocumentElement();
	}

	/**
	 * @return Wymiary planszy ("świata gry"). Wymiary poziomu wplywają na poziom trudności.
     */
	public Dimension getGameWorldSize() throws XPathExpressionException {

		int gameWorldWidth  = Integer.parseInt(xpath.compile("//gameWorldSize/width").evaluate(xmlDocument));
		int gameWorldHeight = Integer.parseInt(xpath.compile("//gameWorldSize/height").evaluate(xmlDocument));

		return new Dimension(gameWorldWidth, gameWorldHeight);
	}


	void loadBalls(GameLevel level) throws XPathExpressionException {
		//List<Node> balls = filterChildrenElements(findChildByName(root, "balls").getChildNodes());
		NodeList balls = (NodeList) xpath.compile("//balls/ball").evaluate(xmlDocument, XPathConstants.NODESET);

		for(int i = 0; i<balls.getLength(); i++) {	//foreach loop does not work for NodeList interface

			int ballPositionX = Integer.parseInt(xpath.compile("./position/x").evaluate(xmlDocument));
			int ballPositionY = Integer.parseInt(xpath.compile("./position/y").evaluate(xmlDocument));

			double ballSpeedX = Double.parseDouble(xpath.compile("./speed/x").evaluate(xmlDocument));
			double ballSpeedY = Double.parseDouble(xpath.compile("./speed/y").evaluate(xmlDocument));
			
			int ballLevel = Integer.parseInt(xpath.compile("./level").evaluate(xmlDocument));
			
			level.getBalls().add(
				new Ball(
					new Point(ballPositionX, ballPositionY),
					ballLevel,
					new double[] { ballSpeedX, ballSpeedY }));
		}
	}

	/**
	 *
	 * @param level
     */
	public void setupPlayerAvatar(GameLevel level) throws XPathExpressionException {
		Dimension gameLevelSize = getGameWorldSize();
		PlayerAvatar avatar = level.getPlayerAvatar();
		Node playerNode = findChildByName(root, "player");
		String playerPosition = findChildByName(playerNode, "position").getTextContent();
		
		if(playerPosition.equals("left")) {
			avatar.moveTo(0, gameLevelSize.height - PlayerAvatar.getHeight());
		} else if(playerPosition.equals("center")) {
			avatar.moveTo(
				gameLevelSize.width / 2 - PlayerAvatar.getWidth() / 2,
				gameLevelSize.height - PlayerAvatar.getHeight());
		} else if(playerPosition.equals("right")) {
			avatar.moveTo(
				gameLevelSize.width - PlayerAvatar.getWidth(),
				gameLevelSize.height - PlayerAvatar.getHeight());
		} else {
			throw new RuntimeException("Unexpected player position in level config! Expected: left|center|right");
		}
	}

	/**
	 *
	 * @return
     */
	public int getTimeForLevel() throws XPathExpressionException {

		return Integer.parseInt(xpath.compile("//time").evaluate(xmlDocument));
	}

	/**
	 *
	 * @return
     */
	public Map<ExtraObjectType, Double> getExtraObjectsProbabilities() {
		Map<ExtraObjectType, Double> probabilities = new HashMap<ExtraObjectType, Double>();
		
		List<Node> probabilityNodes = filterChildrenElements(findChildByName(root, "extraObjects").getChildNodes());
		
		for(Node probabilityNode : probabilityNodes) {
			double probability = Double.valueOf(probabilityNode.getTextContent());
			if(probability < 0.0 || probability > 1.0) {
				throw new RuntimeException("Probability " + probability + " out of range [0, 1]!");
			}
			probabilities.put(
				ExtraObjectType.valueOf(probabilityNode.getNodeName()),
				probability);
		}
		
		return probabilities;
	}

}
