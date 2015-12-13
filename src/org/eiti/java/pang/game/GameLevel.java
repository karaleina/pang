package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eiti.java.pang.config.XMLGameLevelConfiguration;
import org.eiti.java.pang.game.events.GameLevelUpdateListener;
import org.eiti.java.pang.game.events.MissileWindowExitListener;
import org.eiti.java.pang.globalConstants.GlobalConstantsLoader;
import org.eiti.java.pang.globalConstants.ImageLoader;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.Drawable;
import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.utils.KeyboardMonitor;

import javax.xml.xpath.XPathExpressionException;

/**
 * Klasa rysująca dany poziom.
 */
public class GameLevel implements Drawable {

	private int levelNumber;
	private int timeLeft;
	private Dimension gameWorldSize;
	
	private PlayerAvatar playerAvatar;
	private long lastShotTimestamp;
	
	private Collection<Ball> balls;
	private Collection<ExtraObject> extraObjects;
	
	private Collection<Missile> missiles;
	private Set<Missile> missilesMarkedForRemoval = new HashSet<>();
	
	private ExtraObjectsCreator extraObjectsCreator;

	private Set<GameLevelUpdateListener> changeListeners = new HashSet<>();

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
			this.extraObjectsCreator = new ExtraObjectsCreator(configuration.getExtraObjectsProbabilities());
		} catch (XPathExpressionException e) {
			String errorMessage = "invalid level" + levelNumber + ".xml file";
			System.out.println(errorMessage);
			e.printStackTrace();
		}

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
	
	public void spawnExtraObjects() {
		extraObjects.addAll(extraObjectsCreator.tryToCreateExtraObjects(this));
	}

	public void update(double dt) {
		for (GameObject b : balls) {
			b.move(dt);
		}
		for (Missile m : missiles) {
			m.move(dt);
		}
		removeMarkedMissiles();
		shootMissile();
		movePlayerAvatar(dt);
		fireGameLevelChangedEvent();
	}
	
	private void markForRemoval(Missile missile) {
		missilesMarkedForRemoval.add(missile);
	}
	
	private void removeMarkedMissiles() {
		missiles.removeAll(missilesMarkedForRemoval);
		missilesMarkedForRemoval.clear();
	}
	
	private void shootMissile() {
		if(KeyboardMonitor.isKeyPressed(KeyEvent.VK_SPACE)) {
			long timestamp = System.currentTimeMillis();
			if(timestamp - lastShotTimestamp >= GlobalConstantsLoader.minTimeBetweenShots) {
				final Missile shotMissile = playerAvatar.shoot();
				shotMissile.addMissileWindowExitListener(new MissileWindowExitListener() {
					@Override
					public void onMissileWindowExit(Missile missile) {
						shotMissile.removeMissileWindowExitListener(this);
						// we mark missiles for removal to avoid
						// ConcurrentModificationException in update()
						markForRemoval(missile);
					}
				});
				missiles.add(shotMissile);
				lastShotTimestamp = timestamp;
			}
		}
	}
	
	private void movePlayerAvatar(double dt) {
		if(KeyboardMonitor.isKeyPressed(KeyEvent.VK_LEFT) &&
				!KeyboardMonitor.isKeyPressed(KeyEvent.VK_RIGHT)) {
			playerAvatar.setVelocity(-GlobalConstantsLoader.playerVelocity);
		} else if(KeyboardMonitor.isKeyPressed(KeyEvent.VK_RIGHT) &&
				!KeyboardMonitor.isKeyPressed(KeyEvent.VK_LEFT)) {
			playerAvatar.setVelocity(GlobalConstantsLoader.playerVelocity);
		} else {
			playerAvatar.setVelocity(0.0);
		}
		playerAvatar.move(dt);
	}
	
	public void addGameLevelUpdatedListener(GameLevelUpdateListener listener) {
		changeListeners.add(listener);
	}
	
	public void removeGameLevelUpdatedListener(GameLevelUpdateListener listener) {
		changeListeners.remove(listener);
	}

	@Override
	public void draw(Graphics g) {
		playerAvatar.draw(g);
		
		for(GameObject b : balls) {
			b.draw(g);
		}
		for(Missile m : missiles) {
			m.draw(g);
		}
		for(ExtraObject e : extraObjects) {
			e.draw(g);
		}
		
		drawLives(g);
	}

	private void fireGameLevelChangedEvent() {
		for(GameLevelUpdateListener listener : changeListeners) {
			listener.onGameLevelUpdated();
		}
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
