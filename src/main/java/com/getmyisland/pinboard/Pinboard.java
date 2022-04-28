package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Pinboard extends JPanel {
    public Pinboard() {
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(2500, 2500));
        setLayout(new DragLayout());
        
        // Load data
    }
}