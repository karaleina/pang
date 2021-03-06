package org.eiti.java.pang.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.swing.*;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;
import org.eiti.java.pang.game.HighScoreEntry;

/**
 * BestScoresDalog shows a list of best scores (stored locally or on a server).
 */
public class BestScoresDialog extends JDialog {

    public BestScoresDialog(XMLBestScoresIO bestScoresProvider) throws Exception {

        List<HighScoreEntry> highScoreEntries = bestScoresProvider.getEntries();

        setTitle("Best Scores");
        setSize(400, 280);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1,2));

        JPanel panel = new JPanel();
        add(panel);


        Object[][] tableContent = new Object[highScoreEntries.size()][2];
        String[] columnNames = {"Player", "Score"};
        for (int i = 0; i < highScoreEntries.size(); i++) {
            tableContent[i][0] = highScoreEntries.get(i).getNickname();
            tableContent[i][1] = highScoreEntries.get(i).getScore();
        }
        JTable table = new JTable(tableContent, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(380,200));

        table.setFillsViewportHeight(true);
        panel.add(scrollPane);

        JButton ok = new JButton("OK");
        panel.add(ok, BorderLayout.SOUTH);

        ok.addActionListener(e -> this.dispose());  //e is an ActionListener

    }
}
