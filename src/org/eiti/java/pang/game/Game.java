package org.eiti.java.pang.game;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.eiti.java.pang.config.ConfigurationProvider;
import org.eiti.java.pang.config.LocalConfigurationProvider;
import org.eiti.java.pang.config.NetworkConfigurationProvider;
import org.eiti.java.pang.config.xml.XMLGlobalConfiguration;
import org.eiti.java.pang.game.events.BallDestroyedListener;
import org.eiti.java.pang.game.events.GameFinishedListener;
import org.eiti.java.pang.game.events.GameLevelChangedListener;
import org.eiti.java.pang.game.events.NoBallsLeftListener;
import org.eiti.java.pang.game.events.TimeLeftChangedListener;
import org.eiti.java.pang.game.events.PlayerHitByBallListener;
import org.eiti.java.pang.global.GlobalConstants;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.StandardWeapon;
import org.eiti.java.pang.network.ServerConnection;

public class Game {
	
	private ServerConnection connection;
	
	private ConfigurationProvider configurationProvider;

	private GameStatus status;
	
	private GameLevel level;

	private GameThread gameThread;
	
	private GameScore score = new GameScore();
	
	private Set<GameLevelChangedListener> gameLevelChangedListeners = new HashSet<>();
	private Set<GameFinishedListener> gameFinishedListeners = new HashSet<>();

	private String nickname;
	
	private int startingLives = GlobalConstants.initialLives;


	public Game() {
		reset();
	}

	public void reset(GameInitParameters initParameters) throws Exception {
		setupConnectionAndProvider(initParameters);
		updateGlobalConfiguration();
		nickname = initParameters.getNickname();
		status = GameStatus.NOT_STARTED;
		clearBoard();
		score.clear();
	}

	private void reset() {
		try {
			reset(GameInitParameters.local("Anonymous"));
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	private void setupConnectionAndProvider(GameInitParameters initParameters) throws Exception {
		if(connection != null) {
			connection.close();
		}
		if(initParameters.isLocal()) {
			connection = null;
			configurationProvider = new LocalConfigurationProvider();
		} else {
			connection = new ServerConnection(
				initParameters.getServerAddress(),
				initParameters.getServerPort());
			configurationProvider = new NetworkConfigurationProvider(connection);
		}
	}
	
	private void updateGlobalConfiguration() throws Exception {
		XMLGlobalConfiguration configuration = configurationProvider.getGlobalConfiguration();
		GlobalConstants.setConstants(configuration);
	}
	
	public ConfigurationProvider getConfigurationProvider() {
		return configurationProvider;
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
		int nextLevelNumber = (level == null) ? 1 : level.getLevelNumber() + 1;
		checkLevelExistence(nextLevelNumber);
		level = getGameLevel(nextLevelNumber);
		setupLevel();
		PlayerAvatar.getInstance().setWeapon(new StandardWeapon(new Point2D.Double(0, 0),
				GlobalConstants.GAME_WORLD_SIZE));
	}
	
	private void checkLevelExistence(int nextLevelNumber) throws NoMoreLevelsException {
		try {
			if(!configurationProvider.levelExists(nextLevelNumber)) {
				throw new NoMoreLevelsException();
			}
		} catch(NoMoreLevelsException exc) {
			throw exc;
		}
		catch(Exception exc) {
			throw new RuntimeException(exc);
		}
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
				int pointsForBall = powerOf2(ballLevel);
				if(PlayerAvatar.getInstance().getWeapon() instanceof StandardWeapon) {
					// 2 ^ ballLevel * levelNumber
					score.updateScore(pointsForBall * level.getLevelNumber());
				} else {
					// Ball was destroyed by super weapon, player gets all points he
					// would get by destroying the ball and all its "children balls"
					// plus some bonus.
					int ballAndChildrenEquivalent = pointsForBall * ballLevel * level.getLevelNumber();
					final int bonus = 10;
					score.updateScore(ballAndChildrenEquivalent + bonus);
				}
			}
			
			private int powerOf2(int x) {
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
					finish();
				}
			}
		});
		level.addPlayerHitByBallListener(new PlayerHitByBallListener() {
			@Override
			public void onPlayerHitByBall() {
				PlayerAvatar.getInstance().setLives(PlayerAvatar.getInstance().getLives() - 1);
				if(PlayerAvatar.getInstance().getLives() <= 0) {
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
		cleanupAfterGame();
		fireGameFinishedEvent(false);
	}
	
	public void finish() {
		cleanupAfterGame();
		fireGameFinishedEvent(true);
	}

	private void cleanupAfterGame() {
		gameThread.interrupt();
		clearBoard();
		status = GameStatus.FINISHED;
		updateBestScores();
	}

	private void clearBoard() {
		level = null;
		fireGameLevelChangedEvent();
	}
	
	private void updateBestScores() {
		try {
			configurationProvider.updateBestScores(nickname, score.getNumericScore());
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
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
	
	private GameLevel getGameLevel(int levelNumber) {
		try {
			return new GameLevel(
				levelNumber,
				configurationProvider.getLevelConfiguration(levelNumber),
				PlayerAvatar.getInstance());
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	private static class NoMoreLevelsException extends Exception {
		private static final long serialVersionUID = 1L;
	}

}
