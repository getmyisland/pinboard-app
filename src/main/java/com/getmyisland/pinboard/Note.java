package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Note extends JPanel {
    /** The title of the note. */
    private final String noteTitle;
    
    /** The JLabel of the {@link #noteTitle}. */
    private final JLabel noteTitleLabel;
    
    /** The JTextArea containing the note description. IF IS TEXT NOTE. */
    private final JTextArea noteDescriptionTextArea;
    
    /** The file path to the note image. IF IS IMAGE NOTE. */
    private final String noteImageFilePath;
    
    // Variables to drag the note around
    private int x;
    private int y;
    
    public Note(final String noteTitle) {
        this.noteTitle = noteTitle;
        
        // Set the layout
        setLayout(new BorderLayout());
        
        // Set the background color
        setBackground(new Color(240, 240, 240));
        
        // Set the border
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));
        
        // Set the preferred size
        setPreferredSize(new Dimension(300, 445));

        // Drag events
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

        // Create the noteTitleLabel and style it
        noteTitleLabel = new JLabel(noteTitle);
        noteTitleLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        noteTitleLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 0)));
        add(noteTitleLabel, BorderLayout.PAGE_START);
        
        // Create the noteDescriptionTextArea and style it
        noteDescriptionTextArea = new JTextArea();
        noteDescriptionTextArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        noteDescriptionTextArea.setLineWrap(true);
        noteDescriptionTextArea.setBackground(new Color(225, 225, 225));
        noteDescriptionTextArea.setBorder(new EmptyBorder(new Insets(5, 10, 10, 5)));
        add(noteDescriptionTextArea, BorderLayout.CENTER);
        
        // Note is a text note == No image
        noteImageFilePath = null;
    }
    
    public Note(final String noteTitle, final String noteImagePath) {
        this.noteTitle = noteTitle;
        
        // Set the layout
        setLayout(new BorderLayout());
        
        // Set the background color
        setBackground(new Color(240, 240, 240));
        
        // Set the border
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));

        // Drag events
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

        // Create noteTitleLabel and style it
        noteTitleLabel = new JLabel(noteTitle);
        noteTitleLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        noteTitleLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 0)));
        add(noteTitleLabel, BorderLayout.PAGE_START);
        
        // Note is a image note == No Description
        noteDescriptionTextArea = null;
        
        // Get the note image file from file path and create a JLabel which contains the image
        noteImageFilePath = noteImagePath;
        JLabel noteImageLabel;
        try {
            // Read the image file
            BufferedImage bufferedImage = ImageIO.read(new File(noteImagePath));
            Image resizedImage;
           
            // Resize the image if too big
            if(bufferedImage.getWidth() > 1000 || bufferedImage.getHeight() > 1000) {
                int truncatedWidth = bufferedImage.getWidth() / 2;
                int truncatedHeight = bufferedImage.getHeight() / 2;
                
                if(truncatedWidth > 1000 || truncatedHeight > 1000) {
                    resizedImage = bufferedImage.getScaledInstance(bufferedImage.getWidth() / 4, bufferedImage.getHeight() / 4, Image.SCALE_SMOOTH);
                } else {
                    resizedImage = bufferedImage.getScaledInstance(bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2, Image.SCALE_SMOOTH);                }
            } else {
                resizedImage = bufferedImage;
            }
            
            noteImageLabel = new JLabel(new ImageIcon(resizedImage));
        } catch (IOException e) {
            // Create a JLabel with error message
            noteImageLabel = new JLabel("Image not found");
            e.printStackTrace();
        }
        add(noteImageLabel, BorderLayout.CENTER);
    }
    
    /** Get the {@link #noteTitle} */
    public String getNoteTitle() {
        return this.noteTitle;
    }
    
    /** Get the {@link #noteTitleLabel} */
    public JLabel getNoteTitleLabel() {
        return this.noteTitleLabel;
    }
    
    /** Get the {@link #noteDescriptionTextArea} */
    public JTextArea getNoteDescriptionTextArea() {
        return this.noteDescriptionTextArea;
    }
    
    /** Get the {@link #noteImageFilePath} */
    public String getNoteImageFilePath() {
        return this.noteImageFilePath;
    }
}