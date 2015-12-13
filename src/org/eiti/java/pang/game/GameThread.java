package org.eiti.java.pang.game;

public class GameThread extends Thread {
	
	private final GameLevel level;
	
	private long previousUpdateTimestamp;
	
	private static final int UPDATE_INTERVAL_MILIS = 25;
	
	private static final int WARMUP_TIME_SECONDS = 3;
	
	public GameThread(GameLevel level) {
		this.level = level;
	}
	
	@Override
	public void run() {
		try {
		Thread.sleep(WARMUP_TIME_SECONDS * 1000);
			while(!isInterrupted()) {
				double dt = getTimeDiff();
				level.update(dt);
				Thread.sleep(UPDATE_INTERVAL_MILIS);
			}
		} catch(InterruptedException exc) {}
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
