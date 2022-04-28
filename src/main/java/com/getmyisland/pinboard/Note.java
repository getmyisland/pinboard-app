package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Note extends JPanel {
    private int x;
    private int y;
    
    public Note(final String noteTitle) {
        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layout);
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));
        setPreferredSize(new Dimension(400, 90));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent ev) {
                x = ev.getX();
                y = ev.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                int xx = evt.getXOnScreen() - x;
                int yy = evt.getYOnScreen() - y;
                setLocation(xx, yy);

            }
        });

        JLabel titleTextField = new JLabel(noteTitle);
        titleTextField.setFont(new Font("Dialog", Font.BOLD, 35));
        add(titleTextField);
        
        JTextArea descriptionTextField = new JTextArea();
        descriptionTextField.setFont(new Font("Dialog", Font.PLAIN, 18));
        descriptionTextField.setMaximumSize(getPreferredSize());
        add(descriptionTextField);
    }
}