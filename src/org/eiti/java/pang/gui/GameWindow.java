package org.eiti.java.pang.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = -8792460779215020552L;

	public GameWindow() {
		setTitle("Pang");
		setSize(650, 400);//TO DO skalowanie? size narzucony z góry 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		getContentPane().add(new GamePanel(), BorderLayout.CENTER);
	}
	
}
