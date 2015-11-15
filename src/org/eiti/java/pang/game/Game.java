package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Point;

import org.eiti.java.pang.model.PlayerAvatar;

public class Game {
	
	private GameStatus status;
	
	private GameLevel level;
	
	private PlayerAvatar playerAvatar;
	
	private long score;
	
	private Dimension windowSize;
	
	public final static int STARTING_LIVES = 5;
	
	private GameLevelConfiguration[] configurations = new GameLevelConfiguration[] {
		new ExampleGameLevelConfiguration()
	};
	
	public Game(Dimension windowSize) {
		this.windowSize = windowSize;
		status = GameStatus.NOT_STARTED;
		playerAvatar = new PlayerAvatar(
			new Point(
				windowSize.width / 2 - PlayerAvatar.PLAYER_AVATAR_WIDTH / 2,
				windowSize.height - PlayerAvatar.PLAYER_AVATAR_HEIGHT),
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
		return new GameLevel(levelNumber, configurations[levelNumber - 1], playerAvatar, windowSize);
	}

}
