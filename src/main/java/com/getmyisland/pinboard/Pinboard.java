package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Pinboard {
    public Pinboard() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setBackground(new Color(255, 255, 255));
        
        // Detect the screen size and set it to the preferred size
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pinboard App");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
