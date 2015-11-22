package org.eiti.java.pang.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import org.eiti.java.pang.game.Game;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = -8792460779215020552L;

	public GameWindow() {
		setTitle("Pang");
		setSize(650, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		setJMenuBar(new MenuBar());
		
		Game game = new Game();
		game.nextLevel();
		game.start();
		
		// TODO temporary, to show creation of extra objects
		game.getLevel().spawnExtraObjects();
		
		getContentPane().add(new GamePanel(game), BorderLayout.CENTER);
		getContentPane().add(new InfoPanel(game), BorderLayout.NORTH);

	}
	
}
