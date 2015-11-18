package org.eiti.java.pang.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -3254787825789456722L;
	
	private Game game;
	
	public GamePanel() {
		setBackground(new Color(0xffeecc));
		game = new Game();
		game.nextLevel();
		game.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(
			ImageLoader.background,
			0,
			0,
			getWidth(),
			getHeight(),
			null);
		if(game.getLevel() != null) {
			g2.scale(getScaleX(), getScaleY());
			game.getLevel().draw(g2);
		}
	}
	
	private double getScaleX() {
		return getWidth() / game.getLevel().getGameWorldSize().getWidth();
	}
	
	private double getScaleY() {
		return getHeight() / game.getLevel().getGameWorldSize().getHeight();
	}

}
