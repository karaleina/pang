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
		setJMenuBar(new MenuBar());
		getContentPane().add(new GamePanel(), BorderLayout.CENTER);

	}
	
}
