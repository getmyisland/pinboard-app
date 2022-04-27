package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar {
    public JPanel getToolbar() {
        JPanel panel = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setVgap(30);
        layout.setHgap(15);
        panel.setLayout(layout);
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 218, 218)));
        
        // Create new board button
        JButton createNewNotesButton = new JButton("Create new Notes");
        createNewNotesButton.setForeground(Color.BLACK);
        createNewNotesButton.setFocusPainted(false);
        createNewNotesButton.setRolloverEnabled(false);
        createNewNotesButton.setBackground(new Color(230, 230, 230));
        createNewNotesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action performed");
            }
        });
        panel.add(createNewNotesButton);
        
        return panel;
    }
}