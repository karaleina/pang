package org.eiti.java.pang.game;

/**
 * Within this thread game model is updated and game view is drawn.
 */
public class GameThread extends Thread {
	
	private final GameLevel level;
	
	private long timeElapsed;
	
	private long previousUpdateTimestamp;

	private boolean paused;
	
	private static final int UPDATE_INTERVAL_MILIS = 25;
	
	private static final int WARMUP_TIME_SECONDS = 3;

	/**
	 *
	 * @param level Current level.
     */
	public GameThread(GameLevel level) {
		this.level = level;
		paused = false;
	}

	/**
	 * Thread is sleeping 3 seconds (WARMUP_TIME_SECONDS * 1000) after start and later is woken up every 25 milliseconds (UPDATE_INTERVAL_MILIS).
	 Level is updated always but in PAUSE mode time difference is always 0.
	 */
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

	/**
	 * Update timestamp and return difference from previous one.
	 * @return Time difference in miliseconds named out of there as dt.
	 * Note: if game is paused (paused == false) method returns always 0.
     */
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

	/**
	 * If thread is "paused", it does not wait, but model recieve 0 as time difference
	 * @param isPaused true/false
     */
	public void setPause(boolean isPaused){
		paused = isPaused;
	}

}
