package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.eiti.java.pang.config.XMLGameLevelConfiguration;
import org.eiti.java.pang.game.events.GameLevelChangedListener;
import org.eiti.java.pang.model.PlayerAvatar;

public class Game {
	
	private GameStatus status;
	
	private GameLevel level;

	private GameThread gameThread;
	
	private PlayerAvatar playerAvatar;
	
	private GameScore score;
	
	private Set<GameLevelChangedListener> gameLevelChangedListeners = new HashSet<>();
	
	public final static int STARTING_LIVES = 5;
	
	public final static Dimension GAME_WORLD_SIZE = new Dimension(800, 450);
	
	public Game() {
		status = GameStatus.NOT_STARTED;
		level = null;
		score = new GameScore();
		playerAvatar = new PlayerAvatar(
			new Point2D.Double(
				GAME_WORLD_SIZE.width / 2 - PlayerAvatar.getWidth() / 2,
				GAME_WORLD_SIZE.height - PlayerAvatar.getHeight()),
			STARTING_LIVES,
			GAME_WORLD_SIZE);
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public GameLevel getLevel() {
		return level;
	}
	
	public GameScore getScore() {
		return score;
	}
	
	public void nextLevel() {
		if(level == null) {
			level = getGameLevel(1);
		} else {
			level = getGameLevel(level.getLevelNumber() + 1);
		}
		if(gameThread != null) {
			gameThread.interrupt();
		}
		gameThread = new GameThread(level, score);
		fireGameLevelChanged();
	}

	public void start() {
		gameThread.start();
		status = GameStatus.RUNNING;
	}
	
	public void addGameLevelChangedListener(GameLevelChangedListener listener) {
		gameLevelChangedListeners.add(listener);
	}
	
	public void fireGameLevelChanged() {
		for(GameLevelChangedListener listener : gameLevelChangedListeners) {
			listener.onGameLevelChanged(level);
		}
	}
	
	private GameLevel getGameLevel(int levelNumber) {
		try {
			String levelPath = "res/config/level";
			levelPath += levelNumber;
			levelPath += ".xml";

			return new GameLevel(
				levelNumber,
				new XMLGameLevelConfiguration(levelPath),
				playerAvatar);
		} catch(Exception exc) {
			throw new RuntimeException("Failed to load configuration file!", exc);
		}
	}

	//TODO private GlobalVariables
}
