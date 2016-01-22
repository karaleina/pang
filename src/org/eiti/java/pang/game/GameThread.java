package org.eiti.java.pang.game;

public class GameThread extends Thread {
	
	private final GameLevel level;
	
	private long timeElapsed;
	
	private long previousUpdateTimestamp;

	private boolean paused;
	
	private static final int UPDATE_INTERVAL_MILIS = 25;
	
	private static final int WARMUP_TIME_SECONDS = 3;
	
	public GameThread(GameLevel level) {
		this.level = level;
		paused = false;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(WARMUP_TIME_SECONDS * 1000);
			while(!isInterrupted()) {
				long dt = getTimeDiff();

				timeElapsed += dt;
				level.setTimeLeft(level.getTimeForLevel() - (int) (timeElapsed / 1000));
				level.update((double) dt);
				Thread.sleep(UPDATE_INTERVAL_MILIS);
			}
		} catch(InterruptedException exc) {}
	}
	
	private long getTimeDiff() {

		long currentTimestamp = System.currentTimeMillis();
		long dt;

		if (paused) {
			dt = 0;
		} else if(previousUpdateTimestamp == 0) {
			dt = UPDATE_INTERVAL_MILIS;
		} else {
			dt = currentTimestamp - previousUpdateTimestamp;
		}
		
		previousUpdateTimestamp = currentTimestamp;
		return dt;
	}

	public void togglePause(){
		paused = !paused;
	}

}
