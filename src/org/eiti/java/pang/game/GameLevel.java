package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;

import org.eiti.java.pang.config.XMLGameLevelConfiguration;
import org.eiti.java.pang.globalConstants.ImageLoader;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.Drawable;
import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.Missile;

import javax.xml.xpath.XPathExpressionException;

/**
 * Klasa rysująca dany poziom.
 */
public class GameLevel implements Drawable {

	private int levelNumber;
	private int timeLeft;
	private Dimension gameWorldSize;
	private PlayerAvatar playerAvatar;
	private Collection<Ball> balls;
	private Collection<Missile> missiles;
	private Collection<ExtraObject> extraObjects;


	public GameLevel(
			int levelNumber,
			XMLGameLevelConfiguration configuration,
			PlayerAvatar playerAvatar) {
		
		this.levelNumber = levelNumber; //TODO to też powinno być parsowane
		try {
			timeLeft = configuration.getTimeForLevel();
			gameWorldSize = configuration.getGameWorldSize();
			balls = configuration.getBalls();
			this.playerAvatar = playerAvatar;
			String playerAvatarPosition = configuration.getPlayerAvatarPosition();
			setupPayerAvatar(playerAvatarPosition, playerAvatar);


		} catch (XPathExpressionException e) {
			System.out.println("Invalid level%.xml file");
			e.printStackTrace();
		}
		//this.extraObjectsCreator = new ExtraObjectsCreator(configuration.getExtraObjectsProbabilities());



		missiles = new LinkedList<Missile>();
		extraObjects = new LinkedList<ExtraObject>();
		

	}
	private void setupPayerAvatar(String playerPosition, PlayerAvatar avatar) {

		Dimension gameLevelSize = getGameWorldSize();

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

	public int getLevelNumber() {
		return levelNumber;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
	
	public Collection<Ball> getBalls() {
		return balls;
	}
	
	public Collection<Missile> getMissiles() {
		return missiles;
	}
	
	public Collection<ExtraObject> getExtraObjects() {
		return extraObjects;
	}
	
	public Dimension getGameWorldSize() {
		return gameWorldSize;
	}
	
	public PlayerAvatar getPlayerAvatar() {
		return playerAvatar;
	}
	
	//public void spawnExtraObjects() {
	//	extraObjects.addAll(extraObjectsCreator.tryToCreateExtraObjects(this));
	//}

	@Override
	public void draw(Graphics g) {
		for(Ball b : balls) {
			b.draw(g);
		}
		for(Missile m : missiles) {
			m.draw(g);
		}
		for(ExtraObject e : extraObjects) {
			e.draw(g);
		}
		playerAvatar.draw(g);
		
		drawLives(g);
	}

	
	private void drawLives(Graphics g) {
		final int heartSize = 25;
		final int offset = 5;
		for(int i = 0; i < playerAvatar.getLives(); i++){
			g.drawImage(
				ImageLoader.heartImage,
				gameWorldSize.width - (i + 1) * (heartSize + offset),
				offset,
				heartSize,
				heartSize,
				null);
			
		}
	}
}
