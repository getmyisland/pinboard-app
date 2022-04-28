package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) { 
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        
        Toolbar toolbarPanel = new Toolbar();
        frame.getContentPane().add(toolbarPanel, BorderLayout.PAGE_START);
        
        Pinboard pinboardPanel = new Pinboard();
        frame.getContentPane().add(pinboardPanel, BorderLayout.CENTER);
        
        // Detect the screen size and set it to the preferred size
        frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pinboard App");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}