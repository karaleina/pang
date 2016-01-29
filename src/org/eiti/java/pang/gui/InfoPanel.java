package org.eiti.java.pang.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.game.events.ScoreChangedListener;
import org.eiti.java.pang.game.events.GameLevelChangedListener;
import org.eiti.java.pang.game.events.TimeLeftChangedListener;

/**
 * InfoPanel shows players score, current level and remaining time (in seconds)
 */
public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel scoreLabel;
	
	private JLabel timeLabel;
	
	private JLabel levelLabel;
	
	public InfoPanel(Game game) {
		
		scoreLabel = createScoreLabel(game);
		timeLabel = createTimeLabel();
		levelLabel = createLevelLabel();
		
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
	
	private JLabel createTimeLabel() {
		JLabel timeLabel = new JLabel("Time:");
		timeLabel.setFont(timeLabel.getFont().deriveFont(16.0f));
		return timeLabel;
	}
	
	private JLabel createLevelLabel() {
		JLabel levelLabel = new JLabel("Level:");
		levelLabel.setFont(levelLabel.getFont().deriveFont(16.0f));
		return levelLabel;
	}
	
	private String getScoreString(long score) {
		return "Score: " + String.format("%07d", score);
	}
	
	private String getTimeString(GameLevel level) {
		if(level == null) {
			return "Time:";
		}
		int timeLeft = level.getTimeLeft();
		int minutes = timeLeft / 60;
		int seconds = timeLeft % 60;
		return "Time: " + String.format("%02d:%02d", minutes, seconds);
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
			public void onGameLevelChanged(final GameLevel newLevel) {
				levelLabel.setText(getLevelString(newLevel));
				timeLabel.setText(getTimeString(newLevel));
				if(newLevel != null) {
					newLevel.addTimeLeftChangedListener(new TimeLeftChangedListener() {
						@Override
						public void onTimeLeftChanged(int timeLeft) {
							timeLabel.setText(getTimeString(newLevel));
						}
					});
				}
			}
		});
	}
}
