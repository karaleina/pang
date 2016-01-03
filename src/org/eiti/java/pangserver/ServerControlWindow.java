package org.eiti.java.pangserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ServerControlWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3912681666856825674L;
	
	private JButton startStopButton = new JButton("start");
	
	private JLabel statusLabel = new JLabel();
	
	private JTextField portNumberField = new JTextField("12345");
	
	private PangServer server = new PangServer();
	
	public ServerControlWindow() {
		setupWidgets();
		setupLayout();
		setupEvents();
		setTitle("Pang server");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setupWidgets() {
		statusLabel.setFont(statusLabel.getFont().deriveFont(20.0f));
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portNumberField.setPreferredSize(new Dimension(60, 20));
		updateLabels();
	}
	
	private void setupLayout() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startStopButton);
		
		JPanel portNumberPanel = new JPanel();
		portNumberPanel.add(new JLabel("Port number:"));
		portNumberPanel.add(portNumberField);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(buttonPanel);
		topPanel.add(portNumberPanel);
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(statusLabel, BorderLayout.CENTER);
	}
	
	private void setupEvents() {
		startStopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(server.isRunning()) {
					server.stop();
				} else {
					server.start(Integer.parseInt(portNumberField.getText()));
				}
				updateLabels();
			}
		});
	}
	
	private void updateLabels() {
		if(server.isRunning()) {
			startStopButton.setText("stop");
			statusLabel.setText("Server is running at port " + server.getPortNumber() + ".");
		} else {
			startStopButton.setText("start");
			statusLabel.setText("Server is not running.");
		}
	}

}
