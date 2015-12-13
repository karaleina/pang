package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.eiti.java.pang.config.XMLGameLevelConfiguration;
import org.eiti.java.pang.game.events.BallDestroyedListener;
import org.eiti.java.pang.game.events.GameFinishedListener;
import org.eiti.java.pang.game.events.GameLevelChangedListener;
import org.eiti.java.pang.game.events.NoBallsLeftListener;
import org.eiti.java.pang.game.events.TimeLeftChangedListener;
import org.eiti.java.pang.game.events.PlayerHitByBallListener;
import org.eiti.java.pang.global.GlobalConstantsLoader;
import org.eiti.java.pang.gui.NicknameDialog;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.StandardWeapon;

public class Game {
	
	private GameStatus status;
	
	private GameLevel level;

	private GameThread gameThread;
	
	private PlayerAvatar playerAvatar;
	
	private GameScore score = new GameScore();
	
	private Set<GameLevelChangedListener> gameLevelChangedListeners = new HashSet<>();
	private Set<GameFinishedListener> gameFinishedListeners = new HashSet<>();
	
	private int startingLives = GlobalConstantsLoader.initialLives;
	
	public final static Dimension GAME_WORLD_SIZE = new Dimension(800, 450);
	
	public Game() {
		reset();
	}
	
	public void reset() {
		status = GameStatus.NOT_STARTED;
		level = null;
		fireGameLevelChangedEvent();
		score.clear();
		playerAvatar = new PlayerAvatar(
			new Point2D.Double(
				GAME_WORLD_SIZE.width / 2 - PlayerAvatar.getWidth() / 2,
				GAME_WORLD_SIZE.height - PlayerAvatar.getHeight()),
			startingLives,
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
	
	public void nextLevel() throws NoMoreLevelsException {
		if(level == null) {
			level = getGameLevel(1);
		} else {
			level = getGameLevel(level.getLevelNumber() + 1);
		}
		setupLevel();
		playerAvatar.setWeapon(new StandardWeapon(new Point2D.Double(0, 0), GAME_WORLD_SIZE));
	}
	
	private void setupLevel() {
		setupGameLevelListeners();
		if(gameThread != null) {
			gameThread.interrupt();
		}
		gameThread = new GameThread(level);
		fireGameLevelChangedEvent();
	}
	
	private void resetLevel() {
		try {
			level = getGameLevel(level.getLevelNumber());
			setupLevel();
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	private void setupGameLevelListeners() {
		level.addBallDestroyedListener(new BallDestroyedListener() {
			@Override
			public void onBallDestroyed(Ball b) {
				int ballLevel = b.getBallLevel();
				long pointsForBall = powerOf2(ballLevel);
				if(playerAvatar.getWeapon() instanceof StandardWeapon) {
					// 2 ^ ballLevel * levelNumber
					score.updateScore(pointsForBall * level.getLevelNumber());
				} else {
					// Ball was destroyed by super weapon, player gets all points he
					// would get by destroying the ball and all its "children balls"
					// plus some bonus.
					long ballAndChildrenEquivalent = pointsForBall * ballLevel * level.getLevelNumber();
					final long bonus = 10;
					score.updateScore(ballAndChildrenEquivalent + bonus);
				}
			}
			
			private long powerOf2(int x) {
				return 1 << x;
			}
		});
		level.addNoBallsLeftListener(new NoBallsLeftListener() {
			@Override
			public void onNoBallsLeft() {
				try {
					nextLevel();
					start();
				} catch(NoMoreLevelsException exc) {
					gameFinished();
				}
			}
		});
		level.addPlayerHitByBallListener(new PlayerHitByBallListener() {
			@Override
			public void onPlayerHitByBall() {
				playerAvatar.setLives(playerAvatar.getLives() - 1);
				if(playerAvatar.getLives() <= 0) {
					gameOver();
				} else {
					resetLevel();
					start();
				}
			}
		});
		level.addTimeLeftChangedListener(new TimeLeftChangedListener() {
			@Override
			public void onTimeLeftChanged(int timeLeft) {
				if(timeLeft == 0) {
					gameOver();
				}
			}
		});
	}

	public void start() {
		gameThread.start();
		status = GameStatus.RUNNING;
	}
	
	public void gameOver() {
		gameThread.interrupt();
		reset();
		fireGameFinishedEvent(false);
	}
	
	public void gameFinished() {
		gameThread.interrupt();
		reset();
		fireGameFinishedEvent(true);
	}
	
	public void addGameLevelChangedListener(GameLevelChangedListener listener) {
		gameLevelChangedListeners.add(listener);
	}
	
	private void fireGameLevelChangedEvent() {
		for(GameLevelChangedListener listener : gameLevelChangedListeners) {
			listener.onGameLevelChanged(level);
		}
	}
	
	public void addGameFinishedListener(GameFinishedListener listener) {
		gameFinishedListeners.add(listener);
	}
	
	private void fireGameFinishedEvent(boolean completed) {
		for(GameFinishedListener listener : gameFinishedListeners) {
			listener.onGameFinished(completed);
		}
	}
	
	private GameLevel getGameLevel(int levelNumber) throws NoMoreLevelsException {
		try {
			String levelPath = "res/config/level";
			levelPath += levelNumber;
			levelPath += ".xml";

			return new GameLevel(
				levelNumber,
				new XMLGameLevelConfiguration(levelPath),
				playerAvatar);
		} catch(Exception exc) {
			throw new NoMoreLevelsException();
		}
	}

	//TODO private GlobalVariables
	
	private static class NoMoreLevelsException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
