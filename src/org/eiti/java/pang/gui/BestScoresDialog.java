package org.eiti.java.pang.gui;

import org.eiti.java.pang.config.XMLBestScoresIO;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import javax.swing.*;

/**
 * Created by S. H. on 17.11.15.
 */
public class BestScoresDialog extends JDialog {

    private XMLBestScoresIO bestScoresIO;

    public BestScoresDialog() throws Exception {

        bestScoresIO = new XMLBestScoresIO();
        ArrayList<String>  bestPlayers = bestScoresIO.getBestPlayers();
        ArrayList<Integer> bestScores  = bestScoresIO.getBestScores();

        setTitle("Best Scores");
        setSize(500, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        add(panel);


        Object[][] tableContent = new Object[bestScores.size()][2];
        String[] columnNames = {"Player", "Score"};
        for (int i = 0; i < bestScores.size(); i++) {
            tableContent[i][0] = bestPlayers.get(i);
            tableContent[i][1] = bestScores.get(i);
        }
        JTable table = new JTable(tableContent, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        JButton ok = new JButton("OK");
        panel.add(ok, BorderLayout.SOUTH);

        ok.addActionListener(e -> this.dispose());  //e is an ActionListener

    }
}
