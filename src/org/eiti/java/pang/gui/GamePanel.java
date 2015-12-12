package org.eiti.java.pang.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.game.events.GameLevelChangedListener;
import org.eiti.java.pang.game.events.GameLevelUpdateListener;
import org.eiti.java.pang.globalConstants.ImageLoader;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -3254787825789456722L;
	
	private GameLevel level;
	
	private GameLevelUpdateListener drawOnUpdateListener = new GameLevelUpdateListener() {
		@Override
		public void onGameLevelUpdated() {
			repaint();
		}
	};
	
	public GamePanel(Game game) {
		setBackground(new Color(0xffeecc));
		game.addGameLevelChangedListener(new GameLevelChangedListener() {
			@Override
			public void onGameLevelChanged(GameLevel newLevel) {
				setLevel(newLevel);
			}
		});
	}
	
	public void setLevel(GameLevel level) {
		if(level != null) {
			level.removeGameLevelUpdatedListener(drawOnUpdateListener);
		}
		this.level = level;
		level.addGameLevelUpdatedListener(drawOnUpdateListener);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(
			ImageLoader.background,
			0,
			0,
			getWidth(),
			getHeight(),
			null);
		if(level != null) {
			g2.scale(getScaleX(), getScaleY());
			level.draw(g2);
		}
	}
	
	private double getScaleX() {
		return getWidth() / level.getGameWorldSize().getWidth();
	}
	
	private double getScaleY() {
		return getHeight() / level.getGameWorldSize().getHeight();
	}

}
