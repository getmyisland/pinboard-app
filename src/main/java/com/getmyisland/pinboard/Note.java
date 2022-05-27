package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Note extends JPanel {
    /**
     * Enum to define all different types of notes.
     * 
     * @author MFI
     *
     */
    public enum NoteType {
        TitleDescriptionNote,
        TitleImageNote,
        ImageNote
    }
    
    /** The type of the note. */
    private final NoteType noteType;
    
    /** The title of the note. */
    private final String noteTitle;
    /** The JLabel of the {@link #noteTitle}. */
    private final JLabel noteTitleLabel;

    /** The JTextArea containing the note description. */
    private final JTextArea noteDescriptionTextArea;

    /** The file path to the note image. */
    private final Path noteImageFilePath;

    /** Point to store the starting point when a user clicks on a note. */
    private Point startPoint;

    /** Create a {@code NoteType.TitleDescriptionNote} note. */
    public Note(final String noteTitle) {
        this.noteType = NoteType.TitleDescriptionNote;
        this.noteTitle = noteTitle;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));
        setPreferredSize(new Dimension(400, 500));

        addDragEvents();

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

        noteImageFilePath = null;
    }

    /** Create a {@code NoteType.TitleImageNote} note. */
    public Note(final String noteTitle, final Path noteImageFilePath) {
        this.noteType = NoteType.TitleImageNote;
        this.noteTitle = noteTitle;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));

        addDragEvents();

        noteTitleLabel = new JLabel(noteTitle);
        noteTitleLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        noteTitleLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 0)));
        add(noteTitleLabel, BorderLayout.PAGE_START);

        noteDescriptionTextArea = null;

        // Get the Note image file from the image file path
        this.noteImageFilePath = noteImageFilePath;
        JLabel noteImageLabel;
        try {
            // Read the image file
            BufferedImage bufferedImage = ImageIO.read(this.noteImageFilePath.toFile());
            Image resizedImage;

            // Resize the image if too big
            if (bufferedImage.getWidth() > 1000 || bufferedImage.getHeight() > 1000) {
                int truncatedWidth = bufferedImage.getWidth() / 2;
                int truncatedHeight = bufferedImage.getHeight() / 2;

                if (truncatedWidth > 1000 || truncatedHeight > 1000) {
                    resizedImage = bufferedImage.getScaledInstance(bufferedImage.getWidth() / 4,
                            bufferedImage.getHeight() / 4, Image.SCALE_SMOOTH);
                } else {
                    resizedImage = bufferedImage.getScaledInstance(bufferedImage.getWidth() / 2,
                            bufferedImage.getHeight() / 2, Image.SCALE_SMOOTH);
                }
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
    
    /** Create a {@code NoteType.ImageNote} note. */
    public Note(final Path noteImageFilePath) {
        this.noteType = NoteType.ImageNote;
        this.noteTitle = null;

        setLayout(new FlowLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(218, 218, 218)));

        addDragEvents();

        noteTitleLabel = null;
        noteDescriptionTextArea = null;

        // Get the Note image file from the image file path
        this.noteImageFilePath = noteImageFilePath;
        JLabel noteImageLabel;
        try {
            // Read the image file
            BufferedImage bufferedImage = ImageIO.read(this.noteImageFilePath.toFile());
            Image resizedImage;

            // Resize the image if too big
            if (bufferedImage.getWidth() > 1000 || bufferedImage.getHeight() > 1000) {
                int truncatedWidth = bufferedImage.getWidth() / 2;
                int truncatedHeight = bufferedImage.getHeight() / 2;

                if (truncatedWidth > 1000 || truncatedHeight > 1000) {
                    resizedImage = bufferedImage.getScaledInstance(bufferedImage.getWidth() / 4,
                            bufferedImage.getHeight() / 4, Image.SCALE_SMOOTH);
                } else {
                    resizedImage = bufferedImage.getScaledInstance(bufferedImage.getWidth() / 2,
                            bufferedImage.getHeight() / 2, Image.SCALE_SMOOTH);
                }
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

    /** Adds Drag events to the object. */
    protected void addDragEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = SwingUtilities.convertPoint(getNote(), e.getPoint(), Main.instance.getPinboard());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point location = SwingUtilities.convertPoint(getNote(), e.getPoint(), Main.instance.getPinboard());
                if (Main.instance.getPinboard().getBounds().contains(location)) {
                    Point newLocation = getNote().getLocation();
                    newLocation.translate(location.x - startPoint.x, location.y - startPoint.y);
                    newLocation.x = Math.max(newLocation.x, 0);
                    newLocation.y = Math.max(newLocation.y, 0);
                    newLocation.x = Math.min(newLocation.x,
                            Main.instance.getPinboard().getWidth() - getNote().getWidth());
                    newLocation.y = Math.min(newLocation.y,
                            Main.instance.getPinboard().getHeight() - getNote().getHeight());
                    getNote().setLocation(newLocation);
                    startPoint = location;
                }
            }
        });
    }
    
    /** Get the {@link Note} object. */
    private Note getNote() {
        return this;
    }

    /** Get the {@link #noteType} */
    public NoteType getNoteType() {
        return this.noteType;
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
    public Path getNoteImageFilePath() {
        return this.noteImageFilePath;
    }
}