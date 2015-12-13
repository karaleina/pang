package org.eiti.java.pang.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.utils.KeyboardMonitor;

/**
 * Główne okno gry.
 */
public class GameWindow extends JFrame {

	private static final long serialVersionUID = -8792460779215020552L;

	private Game game;

	public GameWindow() {
		setTitle("Pang");
		setSize(650, 400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		game = new Game();

		// TODO temporary, to show creation of extra objects
		//game.getLevel().spawnExtraObjects();

		getContentPane().add(new GamePanel(game), BorderLayout.CENTER);
		getContentPane().add(new InfoPanel(game), BorderLayout.NORTH);

		setupMenu();

		game.nextLevel();
		game.start();
		
		bindKeyboardEvents();
	}
	
	private void bindKeyboardEvents() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent evt) {
				KeyboardMonitor.setKeyPressed(evt.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent evt) {
				KeyboardMonitor.setKeyReleased(evt.getKeyCode());
			}

			@Override
			public void keyTyped(KeyEvent evt) {}
			
		});
	}

	private void setupMenu() {
		//TODO Cała obsługa menu - później być może popakuje to w klasy, jedna oddzielna kasa MenuBar byłachybionym pomysłem.
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		JMenu gameMenu = new JMenu("Game");
		JMenu settMenu = new JMenu("Settings");
		JMenu helpMenu = new JMenu("Help");

		menu.add(gameMenu);
		menu.add(settMenu);
		menu.add(helpMenu);


		JMenuItem newGame = new JMenuItem("New Game");
		KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK);
		newGame.setAccelerator(ctrlN);
		gameMenu.add(newGame);
		JMenuItem pause = new JMenuItem("Pause");
		KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK);
		pause.setAccelerator(ctrlP);
		gameMenu.add(pause);
		gameMenu.addSeparator();

		JMenuItem nickname = new JMenuItem("Nickname");     //byc moze trzeba bedzie usunac ta pozycje
		gameMenu.add(nickname);
		JMenuItem bestScores = new JMenuItem("Best Scores");
		gameMenu.add(bestScores);
		gameMenu.addSeparator();

		JMenuItem quitGame = new JMenuItem("Quit");
		KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK);
		quitGame.setAccelerator(ctrlQ);
		gameMenu.add(quitGame);

		JMenuItem theme1 = new JMenuItem("Theme 1");
		settMenu.add(theme1);
		JMenuItem theme2 = new JMenuItem("Theme 2");
		settMenu.add(theme2);
		settMenu.addSeparator();

		JMenuItem standardMode = new JMenuItem("Standard Mode");
		settMenu.add(standardMode);
		JMenuItem viMode = new JMenuItem("VI Mode");
		settMenu.add(viMode);

		JMenuItem help = new JMenuItem("Help");
		KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
		help.setAccelerator(f1);
		helpMenu.add(help);
		JMenuItem aboutPang = new JMenuItem("About Pang");
		KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		aboutPang.setAccelerator(f2);
		helpMenu.add(aboutPang);

		//actions

		nickname.addActionListener(e -> {		//e is an ActionListener
            NicknameDialog nicknameDialog = new NicknameDialog();
            nicknameDialog.setVisible(true);
        });

		bestScores.addActionListener(e -> {
            BestScoresDialog bestScoresDialog = new BestScoresDialog();
            bestScoresDialog.setVisible(true);
        });

		quitGame.addActionListener(e -> this.dispose());  //e is an ActionListener


		help.addActionListener(e -> {
            HelpDialog helpDialog = new HelpDialog("Tekst pomocy");
            helpDialog.setVisible(true);
        });

		aboutPang.addActionListener(e -> {
            HelpDialog helpDialog = new HelpDialog("O programie");
            helpDialog.setVisible(true);
        });
	}


}
