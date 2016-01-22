package org.eiti.java.pang.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import org.eiti.java.pang.game.Game;
import org.eiti.java.pang.game.GameInitParameters;
import org.eiti.java.pang.game.GameStatus;
import org.eiti.java.pang.game.events.GameFinishedListener;
import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.utils.KeyboardMonitor;

/**
 * Główne okno gry.
 */
public class GameWindow extends JFrame {

	private static final long serialVersionUID = -8792460779215020552L;

	private Game game;
	private GamePanel gamePanel;
	private InfoPanel infoPanel;

	public GameWindow() {
		setTitle("Pang");
		setSize(650, 400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		try {
			game = new Game();
			setupGameEvents();
		} catch(Exception exc) {
			JOptionPane.showMessageDialog(
				this,
				"Failed to create a local game!\nReason: " + exc.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		// TODO temporary, to show creation of extra objects
		//game.getLevel().spawnExtraObjects();

		gamePanel = new GamePanel(game);
		infoPanel = new InfoPanel(game);
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		getContentPane().add(infoPanel, BorderLayout.NORTH);

		setupMenu();
		bindKeyboardEvents();
	}
	
	private void setupGameEvents() {
		game.addGameFinishedListener(new GameFinishedListener() {
			@Override
			public void onGameFinished(boolean completed) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						showBestScores();
					}
				});
			}
		});
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

		JMenuItem pause = new JCheckBoxMenuItem("Pause");
		KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK);
		pause.setAccelerator(ctrlP);
		gameMenu.add(pause);

		gameMenu.addSeparator();

		JMenuItem bestScores = new JMenuItem("Best Scores");
		KeyStroke ctrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK);
		bestScores.setAccelerator(ctrlB);
		gameMenu.add(bestScores);
		gameMenu.addSeparator();

		JMenuItem quitGame = new JMenuItem("Quit");
		KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK);
		quitGame.setAccelerator(ctrlQ);
		gameMenu.add(quitGame);

		ButtonGroup themes = new ButtonGroup();
		JMenuItem theme1 = new JRadioButtonMenuItem("Solar theme", true);
		themes.add(theme1);
		settMenu.add(theme1);

		JMenuItem theme2 = new JRadioButtonMenuItem("Lunar Theme", false);
		themes.add(theme2);
		settMenu.add(theme2);
		settMenu.addSeparator();

		ButtonGroup modes = new ButtonGroup();
		JMenuItem standardMode = new JRadioButtonMenuItem("Standard Mode", true);
		standardMode.setEnabled(false);
		modes.add(standardMode);
		settMenu.add(standardMode);

		JMenuItem viMode = new JRadioButtonMenuItem("VI Mode", false);
		viMode.setEnabled(false);
		modes.add(viMode);
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
		
		newGame.addActionListener(e -> { 		//e is an ActionListener
			if(game.getStatus() == GameStatus.FINISHED || game.getStatus() == GameStatus.NOT_STARTED) {
				NewGameDialog dlg = new NewGameDialog();
				GameInitParameters initParameters = dlg.showDialog(GameWindow.this);
				
				if(initParameters != null) {
					newGame(initParameters);
				}
			}
		});

		bestScores.addActionListener((e) -> showBestScores());

		pause.addActionListener((e) -> {
			try {
				if (pause.isSelected())
					game.pause();
				else
					game.resume();
			} catch (InterruptedException ex){
				System.out.print("IntrruptException occured while pausing/resuming");
				ex.printStackTrace();
			}
		});

		quitGame.addActionListener((e) -> this.dispose());  //e is an ActionListener

		theme1.addActionListener((e) -> {
			ImageLoader.getInstance().update("res/config/theme1.xml");
			PlayerAvatar.getInstance().resize();
			gamePanel.repaint();
		});
		theme2.addActionListener((e) ->{
			ImageLoader.getInstance().update("res/config/theme2.xml");
			PlayerAvatar.getInstance().resize();
			gamePanel.repaint();
		});

		help.addActionListener(e -> {
            HelpDialog helpDialog = new HelpDialog("Tekst pomocy");
			helpDialog.setLocationRelativeTo(this);
            helpDialog.setVisible(true);
        });

		aboutPang.addActionListener(e -> {
            HelpDialog helpDialog = new HelpDialog("O programie");
			helpDialog.setLocationRelativeTo(this);
            helpDialog.setVisible(true);
        });
	}

	private void newGame(GameInitParameters initParameters) {
		try {
			game.reset(initParameters);
			game.nextLevel();
			game.start();
		} catch(Exception exc) {
			JOptionPane.showMessageDialog(
				GameWindow.this,
				"Cannot load configuration for level 1!");
			System.exit(1);
		}
	}
	
	private void showBestScores() {
		BestScoresDialog bestScoresDialog = null;
		try {
			bestScoresDialog = new BestScoresDialog(
					game.getConfigurationProvider().getBestScores());
			bestScoresDialog.setLocationRelativeTo(this);
			bestScoresDialog.setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(
				GameWindow.this,
				"Cannot load a list of best scores!");
		}
	}

}
