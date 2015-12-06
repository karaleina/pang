package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Point;

import org.eiti.java.pang.config.XMLGameLevelConfiguration;
import org.eiti.java.pang.model.PlayerAvatar;

public class Game {
	
	private GameStatus status;
	
	private GameLevel level;
	
	private GameThread gameThread;
	
	private PlayerAvatar playerAvatar;
	
	private GameScore score;
	
	public final static int STARTING_LIVES = 5;
	
	public final static Dimension GAME_WORLD_SIZE = new Dimension(800, 450);
	
	public Game() {
		status = GameStatus.NOT_STARTED;
		level = null;
		score = new GameScore();
		playerAvatar = new PlayerAvatar(
			new Point(
				GAME_WORLD_SIZE.width / 2 - PlayerAvatar.getWidth() / 2,
				GAME_WORLD_SIZE.height - PlayerAvatar.getHeight()),
			STARTING_LIVES);
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
	}
	
	public void start() {
		gameThread.start();
		status = GameStatus.RUNNING;
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
