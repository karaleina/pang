package org.eiti.java.pang.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Okno HelpDialog wyświetla (krótki) tekst pomocy lub informacje o programie.
 */
public class HelpDialog extends JFrame{
    /**
     *
     * @param helpText Informacja do wyświetlenia
     */
    public HelpDialog(String helpText){
        setTitle("Help");
        setSize(120, 100);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        JLabel helpText1 = new JLabel(helpText, JLabel.CENTER);
        panel.add(helpText1, new GridLayout(2,1));
        JButton ok =new JButton("OK");
        panel.add(ok, BorderLayout.SOUTH);

        ok.addActionListener(e -> this.dispose());  //e is an ActionListener

    }
}
