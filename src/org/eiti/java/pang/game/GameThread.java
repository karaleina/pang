package org.eiti.java.pang.game;

public class GameThread extends Thread {
	
	private final GameLevel level;
	
	private final GameScore score;
	
	private static final int UPDATE_INTERVAL_MILIS = 100;
	
	public GameThread(GameLevel level, GameScore score) {
		this.level = level;
		this.score = score;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			updateGameState();
			//level.update();
			//level.draw();
			try {
				Thread.sleep(UPDATE_INTERVAL_MILIS);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	private void updateGameState() {
		// TODO real game state update
		score.updateScore(1);
	}
}
