package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Pinboard {
    public JPanel getPinboard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setComponentPopupMenu(new PinboardPopup());
        
        return panel;
    }
    
    public class PinboardPopup extends JPopupMenu {
        JMenuItem createNewNoteItem;
        public PinboardPopup() {

            createNewNoteItem = new JMenuItem("Create new Note");
            createNewNoteItem.setRolloverEnabled(false);
            createNewNoteItem.setFocusable(false);
            createNewNoteItem.setFocusPainted(false);
            createNewNoteItem.setBackground(new Color(230, 230, 230));
            createNewNoteItem.setBorder(BorderFactory.createLineBorder(new Color(218, 218, 218), 2));
            createNewNoteItem.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.out.println("Create new note pressed");
                }
            });
            add(createNewNoteItem);
        }
    }
}
