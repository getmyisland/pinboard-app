package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {
    private static JFrame frame;
    
    private static Toolbar toolbarPanel;
    private static Pinboard pinboardPanel;
    
    public static void main(String[] args) { 
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        
        toolbarPanel = new Toolbar();
        frame.getContentPane().add(toolbarPanel, BorderLayout.PAGE_START);
        
        pinboardPanel = new Pinboard();
        JScrollPane pinboardScrollPane = new JScrollPane(pinboardPanel);
        pinboardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(pinboardScrollPane, BorderLayout.CENTER);
        
        // Detect the screen size and set it to the preferred size
        frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pinboard App");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    
    public static void UpdateFrame() {
        frame.revalidate();
        frame.repaint();
    }
    
    public static JFrame getFrame() {
        return frame;
    }
    
    public static Pinboard getPinboard() {
        return pinboardPanel;
    }
}