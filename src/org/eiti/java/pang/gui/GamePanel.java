package org.eiti.java.pang.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.game.events.GameFinishedListener;
import org.eiti.java.pang.game.events.GameLevelChangedListener;
import org.eiti.java.pang.game.events.GameLevelUpdateListener;
import org.eiti.java.pang.global.ImageLoader;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -3254787825789456722L;
	
	private String message = "";
	private Color messageColor = Color.black;
	
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
		game.addGameFinishedListener(new GameFinishedListener() {
			@Override
			public void onGameFinished(boolean completed) {
				if(completed) {
					message = "Congratulations! You won!";
					messageColor = new Color(0x2020aa);
				} else {
					message = "GAME OVER";
					messageColor = Color.red;
				}
				repaint();
			}
		});
	}

	public void setLevel(GameLevel level) {
		if(this.level != null) {
			this.level.removeGameLevelUpdatedListener(drawOnUpdateListener);
		}
		if(level != null) {
			level.addGameLevelUpdatedListener(drawOnUpdateListener);
		}
		this.level = level;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(
			ImageLoader.getInstance().background,
			0,
			0,
			getWidth(),
			getHeight(),
			null);
		if(level != null) {
			g2.scale(getScaleX(), getScaleY());
			level.draw(g2);
		} else {
			g2.setFont(new Font("Tahoma", Font.BOLD, 26));
			drawMessage(g2);
			drawCenteredString(g2, "Press Ctrl+N to start new game");
		}
	}
	
	private void drawMessage(Graphics2D g2) {
		Dimension stringSize = getStringSize(g2, message);
		Color previousColor = g2.getColor();
		g2.setColor(messageColor);
		g2.drawString(
			message,
			getWidth() / 2 - stringSize.width / 2,
			getHeight() / 2 - stringSize.height * 2);
		g2.setColor(previousColor);
	}
	
	private void drawCenteredString(Graphics2D g2, String s) {
		Dimension stringSize = getStringSize(g2, s);
		g2.drawString(
			s,
			getWidth() / 2 - stringSize.width / 2,
			getHeight() / 2 - stringSize.height / 2);
	}
	
	private Dimension getStringSize(Graphics2D g2, String s) {
		Rectangle2D textBounds = g2.getFontMetrics(g2.getFont()).getStringBounds(s, g2);
		return new Dimension((int) textBounds.getWidth(), (int) textBounds.getHeight());
	}
	
	private double getScaleX() {
		return getWidth() / level.getGameWorldSize().getWidth();
	}
	
	private double getScaleY() {
		return getHeight() / level.getGameWorldSize().getHeight();
	}



}
