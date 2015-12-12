package org.eiti.java.pang.game;

public class GameThread extends Thread {
	
	private final GameLevel level;
	
	private final GameScore score;
	
	private long previousUpdateTimestamp;
	
	private static final int UPDATE_INTERVAL_MILIS = 25;
	
	public GameThread(GameLevel level, GameScore score) {
		this.level = level;
		this.score = score;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			double dt = getTimeDiff();
			score.updateScore(1);
			level.update(dt);
			try {
				Thread.sleep(UPDATE_INTERVAL_MILIS);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	private double getTimeDiff() {
		
		long currentTimestamp = System.currentTimeMillis();
		double dt = 0;
		
		if(previousUpdateTimestamp == 0) {
			dt = UPDATE_INTERVAL_MILIS;
		} else {
			dt = currentTimestamp - previousUpdateTimestamp;
		}
		
		previousUpdateTimestamp = currentTimestamp;
		return dt;
	}
}
