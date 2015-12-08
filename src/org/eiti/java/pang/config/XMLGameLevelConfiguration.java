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
import java.util.ArrayList;
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
	}

	/**
	 * @return Wymiary planszy ("świata gry"). Wymiary poziomu wplywają na poziom trudności.
     */
	public Dimension getGameWorldSize() throws XPathExpressionException {

		int gameWorldWidth  = Integer.parseInt(xpath.compile("//gameWorldSize/width").evaluate(xmlDocument));
		int gameWorldHeight = Integer.parseInt(xpath.compile("//gameWorldSize/height").evaluate(xmlDocument));

		return new Dimension(gameWorldWidth, gameWorldHeight);
	}


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
					new Point(ballPositionX, ballPositionY),
					ballLevel,
					new double[] { ballSpeedX, ballSpeedY },
					this.getGameWorldSize()));
		}

        return ballList;
	}

	/**
	 *
	 * @param level
     */
	public void setupPlayerAvatar(GameLevel level) throws XPathExpressionException {
		Dimension gameLevelSize = getGameWorldSize();
		PlayerAvatar avatar = level.getPlayerAvatar();
		String playerPosition = xpath.compile("//player/position").evaluate(xmlDocument);

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
	public Map<ExtraObjectType, Double> getExtraObjectsProbabilities() throws XPathExpressionException {
		Map<ExtraObjectType, Double> probabilities = new HashMap<ExtraObjectType, Double>();

        int extraObjectsNumber = xmlDocument.getElementsByTagName("extraObjects").getLength();
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

}
