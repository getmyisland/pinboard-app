package com.getmyisland.pinboard;

import java.awt.Color;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Pinboard {
    public JPanel getPinboard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setComponentPopupMenu(new PinboardPopup());
        
        return panel;
    }
    
    public class PinboardPopup extends JPopupMenu {
        JMenuItem anItem;
        public PinboardPopup() {
            anItem = new JMenuItem("Click Me!");
            add(anItem);
        }
    }
}
