package org.eiti.java.pang.gui;

import javax.swing.*;

/**
 * Created by S. H. on 17.11.15.
 */
public class HelpDialog extends JFrame{
    public HelpDialog(){
        setTitle("Help");
        setSize(300, 100);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        JLabel helpText = new JLabel("tu bedzie cos");
        panel.add(helpText);
        JButton ok =new JButton("OK");
        panel.add(ok);

    }
}
