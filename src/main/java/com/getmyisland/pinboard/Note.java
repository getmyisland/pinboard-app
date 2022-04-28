package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Note extends JPanel {
    private int x;
    private int y;
    
    public Note(final String noteTitle) {
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));
        setPreferredSize(new Dimension(300, 445));

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
        titleTextField.setFont(new Font("Dialog", Font.BOLD, 25));
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(titleTextField, c);
        
        JTextArea descriptionTextField = new JTextArea();
        descriptionTextField.setFont(new Font("Dialog", Font.PLAIN, 18));
        descriptionTextField.setLineWrap(true);
        descriptionTextField.setBackground(new Color(218, 218, 218));
        descriptionTextField.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(descriptionTextField, c);
    }
}