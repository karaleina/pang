package org.eiti.java.pang.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.eiti.java.pang.game.Game;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -3254787825789456722L;
	
	private Game game;
	
	private final static int WIDTH = 500;
	private final static int HEIGHT = 300;
	
	public GamePanel() {
		setBackground(Color.WHITE);
		game = new Game(new Dimension(WIDTH, HEIGHT));
		game.nextLevel();
		game.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0, 0, WIDTH, HEIGHT);
		if(game.getLevel() != null) {
			game.getLevel().draw(g);
		}
	}

}
