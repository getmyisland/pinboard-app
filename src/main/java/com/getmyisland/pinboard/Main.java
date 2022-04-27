package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) { 
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        
        //JPanel toolbarPanel = new Toolbar().getToolbar();
        //frame.getContentPane().add(toolbarPanel, BorderLayout.PAGE_START);
        
        JPanel pinboardPanel = new Pinboard().getPinboard();
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