package org.eiti.java.pang.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import javax.swing.*;

import org.eiti.java.pang.config.xml.XMLBestScoresIO;

/**
 * Created by S. H. on 17.11.15.
 */
public class BestScoresDialog extends JDialog {

    public BestScoresDialog(XMLBestScoresIO bestScoresProvider) throws Exception {

        ArrayList<String>  bestPlayers = bestScoresProvider.getBestPlayers();
        ArrayList<Integer> bestScores  = bestScoresProvider.getBestScores();

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
