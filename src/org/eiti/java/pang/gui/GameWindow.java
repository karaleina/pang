package org.eiti.java.pang.gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = -8792460779215020552L;

	public GameWindow() {
		setTitle("Pang");
		setSize(650, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		getContentPane().add(new GamePanel(), BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu settMenu = new JMenu("Settings");
		JMenu helpMenu = new JMenu("Help");

		setJMenuBar(menuBar);
		menuBar.add(gameMenu);
		menuBar.add(settMenu);
		menuBar.add(helpMenu);

		JMenuItem newGame = new JMenuItem("New Game CTRL+N");
		gameMenu.add(newGame);
		JMenuItem pause = new JMenuItem("Pause CTRL+P");
		gameMenu.add(pause);
		gameMenu.addSeparator();
		//JMenuItem nickname = new JMenuItem("Nickname");
		//gameMenu.add(nickname);
		JMenuItem bestScores = new JMenuItem("Best Scores");
		gameMenu.add(bestScores);
		gameMenu.addSeparator();
		JMenuItem quitGame = new JMenuItem("Quit CTRL+Q");
		gameMenu.add(quitGame);

		JMenuItem sound = new JMenuItem("Sound");
		settMenu.add(sound);
		JMenuItem mute = new JMenuItem("Mute");
		settMenu.add(mute);
		settMenu.addSeparator();
		JMenuItem theme1 = new JMenuItem("Theme 1");
		settMenu.add(theme1);
		JMenuItem theme2 = new JMenuItem("Theme 2");
		settMenu.add(theme2);
		settMenu.addSeparator();
		JMenuItem standardMode = new JMenuItem("Standard Mode");
		settMenu.add(standardMode);
		JMenuItem viMode = new JMenuItem("VI Mode");
		settMenu.add(viMode);


	}
	
}
