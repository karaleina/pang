package org.eiti.java.pang.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.game.GameLevel;

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
	}
	
	private JLabel createScoreLabel(Game game) {
		JLabel scoreLabel = new JLabel("Score: " + getScoreString(game));
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(16.0f));
		return scoreLabel;
	}
	
	private JLabel createTimeLabel(GameLevel level) {
		JLabel timeLabel = new JLabel("Time: " + getTimeString(level));
		timeLabel.setFont(timeLabel.getFont().deriveFont(16.0f));
		return timeLabel;
	}
	
	private JLabel createLevelLabel(GameLevel level) {
		JLabel levelLabel = new JLabel("Level: " + getLevelString(level));
		levelLabel.setFont(levelLabel.getFont().deriveFont(16.0f));
		return levelLabel;
	}
	
	private String getScoreString(Game game) {
		return String.format("%07d", game.getScore());
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
		return level == null ? "" : String.valueOf(level.getLevelNumber());
	}
}
