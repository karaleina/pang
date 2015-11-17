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
		setBackground(Color.WHITE);
		game = new Game();
		game.nextLevel();
		game.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
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
