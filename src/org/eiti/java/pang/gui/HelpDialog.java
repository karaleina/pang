package org.eiti.java.pang.gui;

import java.awt.*;

import javax.swing.*;

/**
 * HelpDialog shows short instructions for user or basic information about application.
 */
public class HelpDialog extends JFrame{
    /**
     *
     * @param helpText Informacja do wyświetlenia
     */
    public HelpDialog(String helpText){
        setTitle("Help");
        setSize(200, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JTextArea helpTextArea = new JTextArea(helpText);
        helpTextArea.setEditable(false);
        helpTextArea.setMargin(new Insets(5,5,15,15));
        panel.add(helpTextArea);
        JButton ok  =new JButton("OK");
        panel.add(ok, BorderLayout.SOUTH);

        ok.addActionListener(e -> this.dispose());  //e is an ActionListener
        pack();

    }
}
