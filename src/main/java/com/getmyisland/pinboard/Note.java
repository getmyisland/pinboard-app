package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Note extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1110723371213545173L;

    private final String noteTitle;
    
    private final JLabel noteTitleLabel;
    private final JTextArea noteDescriptionTextArea;
    
    private int x;
    private int y;
    
    public Note(final String noteTitle) {
        this.noteTitle = noteTitle;
        setLayout(new BorderLayout());
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

        noteTitleLabel = new JLabel(noteTitle);
        noteTitleLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        noteTitleLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 0)));
        add(noteTitleLabel, BorderLayout.PAGE_START);
        
        noteDescriptionTextArea = new JTextArea();
        noteDescriptionTextArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        noteDescriptionTextArea.setLineWrap(true);
        noteDescriptionTextArea.setBackground(new Color(225, 225, 225));
        noteDescriptionTextArea.setBorder(new EmptyBorder(new Insets(5, 10, 10, 5)));
        add(noteDescriptionTextArea, BorderLayout.CENTER);
    }
    
    public String getNoteTitle() {
        return this.noteTitle;
    }
    
    public JLabel getNoteTitleLabel() {
        return this.noteTitleLabel;
    }
    
    public JTextArea getNoteDescriptionTextArea() {
        return this.noteDescriptionTextArea;
    }
}