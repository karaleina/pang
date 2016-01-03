package org.eiti.java.pang.game;

import java.util.HashSet;
import java.util.Set;

public class GameScore {
	
	private int score;
	
	private Set<ScoreChangedListener> scoreChangedListeners = new HashSet<>();
	
	public GameScore() {}
	
	public GameScore(int score) {
		this.score = score;
	}
	
	public void updateScore(int amountToAdd) {
		score += amountToAdd;
		fireScoreChangedEvent();
	}
	
	public void clear() {
		score = 0;
		fireScoreChangedEvent();
	}
	
	public int getNumericScore() {
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
