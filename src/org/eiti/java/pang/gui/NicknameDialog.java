package org.eiti.java.pang.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Obiekt NicknameDialog będzie służył do wprowadzania nawwy gracza, możliwej do wyświetllenia na liście wyników.
 */
public class NicknameDialog extends JDialog {
    public NicknameDialog(){
        setTitle("Enter nickname");
        setSize(300, 100);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        JTextField nicknameField = new JTextField(100);
        panel.add(nicknameField);
        JButton ok =new JButton("OK");
        panel.add(ok);
        JButton cancel =new JButton("Cancel");
        panel.add(cancel);
    }

}
