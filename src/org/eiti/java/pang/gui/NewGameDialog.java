package org.eiti.java.pang.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.eiti.java.pang.game.GameInitParameters;

public class NewGameDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201813213204335205L;
	
	private JTextField nicknameField = new JTextField("Anonymous");
	
	private JRadioButton localGameOption = new JRadioButton("Local");
	
	private JRadioButton networkGameOption = new JRadioButton("Network");
	
	private JTextField serverAddressField = new JTextField("localhost");
	
	private JTextField serverPortField = new JTextField("12345");
	
	private JButton okButton = new JButton("OK");
	
	private JButton cancelButton = new JButton("Cancel");
	
	private JLabel serverAddressLabel = createRightAlignedLabel("Address:");
	
	private JLabel serverPortLabel = createRightAlignedLabel("Port:");
	
	// we want exit by [x] to work as "cancel"
	private boolean canceled = true;
	
	public NewGameDialog() {
		
		setSize(360, 250);
		setTitle("New game");
		setModal(true);
		
		initWidgets();
		setupLayout();
		setupEvents();
	}
	
	public GameInitParameters showDialog(Component parent) {
		setLocation(
			parent.getWidth() / 2 - getWidth() / 2,
			parent.getHeight() / 2 - getHeight() / 2);
		setLocationRelativeTo(parent);
		setVisible(true);
		if(canceled) {
			return null;
		} else {
			return extractParameters();
		}
	}
	
	private GameInitParameters extractParameters() {
		if(localGameOption.isSelected()) {
			return GameInitParameters.local(nicknameField.getText());
		} else {
			return GameInitParameters.network(
				nicknameField.getText(),
				serverAddressField.getText(),
				Integer.parseInt(serverPortField.getText()));
		}
	}
	
	private void initWidgets() {
		initRadioButtons();
		initServerLocationFields();
	}
	
	private void initRadioButtons() {
		ButtonGroup gameModeButtons = new ButtonGroup();
		gameModeButtons.add(localGameOption);
		gameModeButtons.add(networkGameOption);
		localGameOption.setSelected(true);
	}
	
	private void initServerLocationFields() {
		serverAddressLabel.setEnabled(false);
		serverAddressField.setEnabled(false);
		serverPortLabel.setEnabled(false);
		serverPortField.setEnabled(false);
	}
	
	private void setupEvents() {
		localGameOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverAddressLabel.setEnabled(false);
				serverAddressField.setEnabled(false);
				serverPortLabel.setEnabled(false);
				serverPortField.setEnabled(false);
			}
		});
		networkGameOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverAddressLabel.setEnabled(true);
				serverAddressField.setEnabled(true);
				serverPortLabel.setEnabled(true);
				serverPortField.setEnabled(true);
			}
		});
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = true;
				dispose();
			}
		});
	}
	
	private void setupLayout() {
		setLayout(new BorderLayout());
		getContentPane().add(createNicknamePanel(), BorderLayout.NORTH);
		getContentPane().add(createGameModePanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createNicknamePanel() {
		JPanel nicknamePanel = new JPanel();
		nicknamePanel.add(new JLabel("Nickname:"));
		nicknamePanel.add(nicknameField);
		nicknameField.setPreferredSize(new Dimension(125, 25));
		return nicknamePanel;
	}
	
	private JPanel createGameModePanel() {
		
		JPanel gameModePanel = new JPanel();
		gameModePanel.setLayout(new BoxLayout(gameModePanel, BoxLayout.X_AXIS));
		gameModePanel.setBorder(new TitledBorder("Game mode"));
		
		gameModePanel.add(createLocalModePanel());
		gameModePanel.add(createNetworkModePanel());
		
		return gameModePanel;
	}
	
	private JPanel createLocalModePanel() {
		JPanel localModePanel = new JPanel();
		localModePanel.add(localGameOption);
		return localModePanel;
	}
	
	private JPanel createNetworkModePanel() {
		
		JPanel networkModePanel = new JPanel();
		networkModePanel.setLayout(new BoxLayout(networkModePanel, BoxLayout.Y_AXIS));
		networkModePanel.add(networkGameOption);
		networkModePanel.add(createVerticalSeparator());
		networkModePanel.add(createServerLocationPanel());
		
		JPanel flowLayout = new JPanel();
		flowLayout.add(networkModePanel);
		
		return flowLayout;
	}
	
	private JComponent createVerticalSeparator() {
		return new JLabel(" ");
	}
	
	private JPanel createServerLocationPanel() {
		
		JPanel serverLocationPanel = new JPanel();
		
		GridLayout gridLayout = new GridLayout(2, 2);
		gridLayout.setHgap(10);
		serverLocationPanel.setLayout(gridLayout);
		
		serverLocationPanel.add(serverAddressLabel);
		serverLocationPanel.add(serverAddressField);
		serverAddressField.setPreferredSize(new Dimension(125, 25));
		
		serverLocationPanel.add(serverPortLabel);
		serverLocationPanel.add(serverPortField);
		serverPortField.setPreferredSize(new Dimension(70, 25));
		
		return serverLocationPanel;
	}
	
	private JLabel createRightAlignedLabel(String labelText) {
		JLabel label = new JLabel(labelText);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}
	
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		return buttonsPanel;
	}

}
