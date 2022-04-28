package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar {
    public Toolbar() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setVgap(20);
        layout.setHgap(15);
        setLayout(layout);
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 218, 218)));
        
        // Create new board button
        ToolbarButton createNotesButton = new ToolbarButton("Create Note");
        createNotesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action performed");
            }
        });
        add(createNotesButton);
        
     // Create new board button
        ToolbarButton testButton = new ToolbarButton("Test");
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action performed");
            }
        });
        add(testButton);
    }
    
    public class ToolbarButton extends JButton {
        public ToolbarButton(final String title) {
            setText(title);
            setForeground(Color.BLACK);
            setFocusPainted(false);
            setRolloverEnabled(false);
            setBackground(new Color(230, 230, 230));
        }
    }
}