package org.eiti.java.pang.game;

import java.util.HashSet;
import java.util.Set;

public class GameScore {
	
	private long score;
	
	private Set<ScoreChangedListener> scoreChangedListeners = new HashSet<>();
	
	public GameScore() {}
	
	public GameScore(long score) {
		this.score = score;
	}
	
	public void updateScore(long amountToAdd) {
		score += amountToAdd;
		fireScoreChangedEvent();
	}
	
	public void clear() {
		score = 0;
		fireScoreChangedEvent();
	}
	
	public long getNumericScore() {
		return score;
	}
	
	public void addScoreChangedListener(ScoreChangedListener listener) {
		scoreChangedListeners.add(listener);
	}
	
	private void fireScoreChangedEvent() {
		for(ScoreChangedListener listener : scoreChangedListeners) {
			listener.onScoreChanged(score);
		}
	}
	
}
