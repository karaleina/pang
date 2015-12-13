package org.eiti.java.pang.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.game.ScoreChangedListener;
import org.eiti.java.pang.game.events.GameLevelChangedListener;

public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel scoreLabel;
	
	private JLabel timeLabel;
	
	private JLabel levelLabel;
	
	public InfoPanel(Game game) {
		GameLevel level = game.getLevel();
		
		scoreLabel = createScoreLabel(game);
		timeLabel = createTimeLabel(level);
		levelLabel = createLevelLabel(level);
		
		FlowLayout panelLayout = new FlowLayout();
		panelLayout.setHgap(50);
		setLayout(panelLayout);
		
		add(scoreLabel);
		add(timeLabel);
		add(levelLabel);
		
		setupListeners(game);
	}

	private JLabel createScoreLabel(Game game) {
		JLabel scoreLabel = new JLabel(getScoreString(game.getScore().getNumericScore()));
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(16.0f));
		return scoreLabel;
	}
	
	private JLabel createTimeLabel(GameLevel level) {
		JLabel timeLabel = new JLabel("Time: " + getTimeString(level));
		timeLabel.setFont(timeLabel.getFont().deriveFont(16.0f));
		return timeLabel;
	}
	
	private JLabel createLevelLabel(GameLevel level) {
		JLabel levelLabel = new JLabel(getLevelString(level));
		levelLabel.setFont(levelLabel.getFont().deriveFont(16.0f));
		return levelLabel;
	}
	
	private String getScoreString(long score) {
		return "Score: " + String.format("%07d", score);
	}
	
	private String getTimeString(GameLevel level) {
		if(level == null) {
			return "";
		}
		int timeLeft = level.getTimeLeft();
		int minutes = timeLeft / 60;
		int seconds = timeLeft % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	private String getLevelString(GameLevel level) {
		return "Level: " + (level == null ? "" : String.valueOf(level.getLevelNumber()));
	}
	
	private void setupListeners(Game game) {
		game.getScore().addScoreChangedListener(new ScoreChangedListener() {
			@Override
			public void onScoreChanged(long newScore) {
				scoreLabel.setText(getScoreString(newScore));
			}
		});
		game.addGameLevelChangedListener(new GameLevelChangedListener() {
			@Override
			public void onGameLevelChanged(GameLevel newLevel) {
				levelLabel.setText(getLevelString(newLevel));
			}
		});
	}
}
