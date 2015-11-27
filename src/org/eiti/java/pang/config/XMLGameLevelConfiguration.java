package org.eiti.java.pang.config;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.w3c.dom.*;

import javax.xml.parsers.*;

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

	public XMLGameLevelConfiguration(File configurationFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(new FileInputStream(configurationFile));
		root = xmlDocument.getDocumentElement();
	}

	/**
	 * @return Wymiary planszy ("świata gry"). Wymiary poziomu wplywają na poziom trudności.
     */
	public Dimension getGameWorldSize() {
		Node gameWorldSize = findChildByName(root, "gameWorldSize");
		int gameWorldWidth = Integer.parseInt(findChildByName(gameWorldSize, "width").getTextContent());
		int gameWorldHeight = Integer.parseInt(findChildByName(gameWorldSize, "height").getTextContent());
		return new Dimension(gameWorldWidth, gameWorldHeight);
	}

	/**
	 * Metoda wczytująca kulki i awatar, por. loadBalls i loadAvatar.
	 * @param level
     */
	public void loadObjects(GameLevel level) {
		loadBalls(level);
		setupPlayerAvatar(level);
	}

	/**
	 *
	 * @param level
     */
	private void loadBalls(GameLevel level) {
		List<Node> balls = filterChildrenElements(findChildByName(root, "balls").getChildNodes());
		
		for(Node ballNode : balls) {
			
			Node ballPosition = findChildByName(ballNode, "position");
			int ballPositionX = Integer.parseInt(findChildByName(ballPosition, "x").getTextContent());
			int ballPositionY = Integer.parseInt(findChildByName(ballPosition, "y").getTextContent());
			
			Node ballSpeed = findChildByName(ballNode, "speed");
			double ballSpeedX = Double.parseDouble(findChildByName(ballSpeed, "x").getTextContent());
			double ballSpeedY = Double.parseDouble(findChildByName(ballSpeed, "y").getTextContent());
			
			int ballLevel = Integer.parseInt(findChildByName(ballNode, "level").getTextContent());
			
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
	private void setupPlayerAvatar(GameLevel level) {
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
	public int getTimeForLevel() {
		Node timeNode = findChildByName(root, "time");
		return Integer.parseInt(timeNode.getTextContent());
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
