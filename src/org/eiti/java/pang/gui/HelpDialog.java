package org.eiti.java.pang.gui;

import javax.swing.*;
import java.awt.*;

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

        ok.addActionListener(e -> this.dispose());

    }
}
