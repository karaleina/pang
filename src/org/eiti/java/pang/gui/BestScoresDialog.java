package org.eiti.java.pang.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by S. H. on 17.11.15.
 */
public class BestScoresDialog extends JDialog {

    public BestScoresDialog(){
        setTitle("Best Scores");
        setSize(300, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        add(panel);

        JTextArea listOfBestScores = new JTextArea(20, 150);
        JScrollPane scrollPane = new JScrollPane(listOfBestScores);
        panel.add(scrollPane);
        JButton ok =new JButton("OK");
        panel.add(ok);

    }
}
