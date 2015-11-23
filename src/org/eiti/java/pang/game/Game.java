package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import org.eiti.java.pang.model.PlayerAvatar;

public class Game {
	
	private GameStatus status;
	
	private GameLevel level;
	
	private PlayerAvatar playerAvatar;
	
	private long score;
	
	public final static int STARTING_LIVES = 5;
	
	public final static Dimension GAME_WORLD_SIZE = new Dimension(800, 450);
	
	public Game() {
		status = GameStatus.NOT_STARTED;
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
	
	public long getScore() {
		return score;
	}
	
	public void nextLevel() {
		if(level == null) {
			level = getGameLevel(1);
		} else {
			level = getGameLevel(level.getLevelNumber() + 1);
		}
		status = GameStatus.NOT_STARTED;
	}
	
	public void start() {
		status = GameStatus.RUNNING;
	}
	
	private GameLevel getGameLevel(int levelNumber) {
		try {
			String levelPath = "res/levels/level";
			levelPath += levelNumber;
			levelPath += ".xml";

			return new GameLevel(
				levelNumber,
				new XMLGameLevelConfiguration(new File(levelPath)),
				playerAvatar);
		} catch(Exception exc) {
			throw new RuntimeException("Failed to load configuration file!", exc);
		}
	}

}
