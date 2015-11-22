package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.PlayerAvatar;
import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLGameLevelConfiguration implements GameLevelConfiguration {
	
	private Document xmlDocument;
	private Element root;
	
	public XMLGameLevelConfiguration(File gameLevelDescriptionFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(new FileInputStream(gameLevelDescriptionFile));
		root = xmlDocument.getDocumentElement();
	}

	@Override
	public Dimension getGameWorldSize() {
		Node gameWorldSize = findChildByName(root, "gameWorldSize");
		int gameWorldWidth = Integer.parseInt(findChildByName(gameWorldSize, "width").getTextContent());
		int gameWorldHeight = Integer.parseInt(findChildByName(gameWorldSize, "height").getTextContent());
		return new Dimension(gameWorldWidth, gameWorldHeight);
	}

	@Override
	public void loadObjects(GameLevel level) {
		loadBalls(level);
		setupPlayerAvatar(level);
	}
	
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
	
	private void setupPlayerAvatar(GameLevel level) {
		Dimension gameLevelSize = getGameWorldSize();
		PlayerAvatar avatar = level.getPlayerAvatar();
		Node playerNode = findChildByName(root, "player");
		String playerPosition = findChildByName(playerNode, "position").getTextContent().toLowerCase();
		
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

	@Override
	public int getTimeForLevel() {
		Node timeNode = findChildByName(root, "time");
		return Integer.parseInt(timeNode.getTextContent());
	}
	
	@Override
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
	
	private Node findChildByName(Node parent, String childName) {
		NodeList children = parent.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals(childName)) {
				return child;
			}
		}
		return null;
	}
	
	private List<Node> filterChildrenElements(NodeList children) {
		List<Node> filteredNodes = new ArrayList<Node>();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
				filteredNodes.add(children.item(i));
			}
		}
		return filteredNodes;
	}

}
