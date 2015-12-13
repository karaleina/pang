package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.eiti.java.pang.config.XMLGameLevelConfiguration;
import org.eiti.java.pang.game.events.BallDestroyedListener;
import org.eiti.java.pang.game.events.GameLevelUpdateListener;
import org.eiti.java.pang.game.events.MissileWindowExitListener;
import org.eiti.java.pang.global.GlobalConstantsLoader;
import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.game.events.NoBallsLeftListener;
import org.eiti.java.pang.game.events.TimeLeftChangedListener;
import org.eiti.java.pang.game.events.PlayerHitByBallListener;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.CollisionOutcome;
import org.eiti.java.pang.model.Drawable;
import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.GameObject;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.utils.KeyboardMonitor;

/**
 * Klasa rysująca dany poziom.
 */
public class GameLevel implements Drawable {

	private int levelNumber;
	private int timeForLevel;
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
	private Set<NoBallsLeftListener> noBallsLeftListeners = new HashSet<>();
	private Set<BallDestroyedListener> ballDestroyedListeners = new HashSet<>();
	private Set<PlayerHitByBallListener> playerHitListeners = new HashSet<>();
	private Set<TimeLeftChangedListener> noTimeLeftListeners = new HashSet<>();

	public GameLevel(
			int levelNumber,
			XMLGameLevelConfiguration configuration,
			PlayerAvatar playerAvatar) {
		
		this.levelNumber = levelNumber; //TODO to też powinno być parsowane
		
		try {
			timeForLevel = configuration.getTimeForLevel();
			timeLeft = timeForLevel;
			gameWorldSize = configuration.getGameWorldSize();
			balls = configuration.getBalls();
			this.playerAvatar = playerAvatar;
			String playerAvatarPosition = configuration.getPlayerAvatarPosition();
			setupPayerAvatar(playerAvatarPosition, playerAvatar);
			extraObjectsCreator = new ExtraObjectsCreator(configuration.getExtraObjectsProbabilities());
		} catch (XPathExpressionException e) {
			String errorMessage = "invalid level" + levelNumber + ".xml file";
			System.out.println(errorMessage);
			e.printStackTrace();
		}

		missiles = new LinkedList<Missile>();
		extraObjects = new LinkedList<ExtraObject>();
		
		addTimeLeftChangedListener(new TimeLeftChangedListener() {
			@Override
			public void onTimeLeftChanged(int timeLeft) {
				extraObjects.addAll(extraObjectsCreator.tryToCreateExtraObjects(GameLevel.this));
			}
		});
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

	public int getTimeForLevel() {
		return timeForLevel;
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	public void setTimeLeft(int timeLeft) {
		if(this.timeLeft != timeLeft) {
			this.timeLeft = timeLeft;
			fireTimeLeftChangedEvent();
		}
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
		
		checkForCollisions();
		
		removeMarkedMissiles();
		shootMissile();
		
		movePlayerAvatar(dt);
		
		fireGameLevelChangedEvent();
		
		if(balls.isEmpty()) {
			fireNoBallsLeftEvent();
		}
	}
	
	private void checkForCollisions() {
		checkPlayerWithBallCollisions();
		checkMissileWithBallCollisions();
		checkPlayerWithExtraObjectCollisions();
	}
	
	private void checkPlayerWithBallCollisions() {
		for(Ball b : balls) {
			if(playerAvatar.collidesWith(b)) {
				firePlayerHitByBallEvent();
			}
		}
	}
	
	private void checkPlayerWithExtraObjectCollisions() {
		Set<ExtraObject> markedForRemoval = new HashSet<>();
		for(ExtraObject extraObject : extraObjects) {
			if(extraObject.collidesWith(playerAvatar)) {
				extraObject.interactWith(playerAvatar);
				markedForRemoval.add(extraObject);
			}
		}
		extraObjects.removeAll(markedForRemoval);
	}
	
	private void checkMissileWithBallCollisions() {
		
		Set<Ball> ballsForSplitting = new HashSet<>();
		Set<Ball> ballsForRemoval = new HashSet<>();
		
		for(Missile m : missiles) {
			for(Ball b : balls) {
				if(m.collidesWith(b)) {
					markForRemoval(m);
					CollisionOutcome outcome = m.collisionOutcome(b);
					switch(outcome) {
					case DESTROY:
						ballsForRemoval.add(b);
						break;
					case SPLIT:
						ballsForSplitting.add(b);
						break;
					default:
						throw new RuntimeException("Unrecognized collision outcome!");
					}
				}
			}
		}
		
		balls.removeAll(ballsForRemoval);
		for(Ball removedBall : ballsForRemoval) {
			fireBallDestroyedEvent(removedBall);
		}
		
		splitBalls(ballsForSplitting);
		removeMarkedMissiles();
	}
	
	private void splitBalls(Collection<Ball> ballsForSplitting) {
		for(Ball baseBall : ballsForSplitting) {
			balls.remove(baseBall);
			fireBallDestroyedEvent(baseBall);
			int baseBallLevel = baseBall.getBallLevel();
			if(baseBallLevel > 1) {
				balls.addAll(baseBall.split());
			}
		}
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
	
	private void fireGameLevelChangedEvent() {
		for(GameLevelUpdateListener listener : changeListeners) {
			listener.onGameLevelUpdated();
		}
	}
	
	public void addNoBallsLeftListener(NoBallsLeftListener listener) {
		noBallsLeftListeners.add(listener);
	}
	
	public void removeNoBallsLeftListener(NoBallsLeftListener listener) {
		noBallsLeftListeners.remove(listener);
	}
	
	private void fireNoBallsLeftEvent() {
		for(NoBallsLeftListener listener : noBallsLeftListeners) {
			listener.onNoBallsLeft();
		}
	}
	
	public void addBallDestroyedListener(BallDestroyedListener listener) {
		ballDestroyedListeners.add(listener);
	}
	
	public void removeBallDestroyedListener(BallDestroyedListener listener) {
		ballDestroyedListeners.remove(listener);
	}
	
	private void fireBallDestroyedEvent(Ball destroyedBall) {
		for(BallDestroyedListener listener : ballDestroyedListeners) {
			listener.onBallDestroyed(destroyedBall);
		}
	}
	
	public void addPlayerHitByBallListener(PlayerHitByBallListener listener) {
		playerHitListeners.add(listener);
	}
	
	public void removePlayerHitByBallListener(PlayerHitByBallListener listener) {
		playerHitListeners.remove(listener);
	}
	
	private void firePlayerHitByBallEvent() {
		for(PlayerHitByBallListener listener : playerHitListeners) {
			listener.onPlayerHitByBall();
		}
	}
	
	public void addTimeLeftChangedListener(TimeLeftChangedListener listener) {
		noTimeLeftListeners.add(listener);
	}
	
	public void removeTimeLeftChangedListener(TimeLeftChangedListener listener) {
		noTimeLeftListeners.remove(listener);
	}
	
	private void fireTimeLeftChangedEvent() {
		for(TimeLeftChangedListener listener : noTimeLeftListeners) {
			listener.onTimeLeftChanged(timeLeft);
		}
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
