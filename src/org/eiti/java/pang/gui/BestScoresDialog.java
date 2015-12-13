package org.eiti.java.pang.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
        JButton ok = new JButton("OK");
        panel.add(ok);

        ok.addActionListener(e -> this.dispose());  //e is an ActionListener

    }
}
