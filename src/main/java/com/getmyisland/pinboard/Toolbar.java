package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Toolbar extends JToolBar {
    public Toolbar() {
        // Create a FlowLayout and configure it
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setVgap(10);
        layout.setHgap(10);
        
        // Apply it to the toolbar
        setLayout(layout);
        
        // Set a background color
        setBackground(new Color(240, 240, 240));
        
        // Set a bottom-only border
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 218, 218)));
        
        // Create a button to open a board from file
        ToolbarButton openFileButton = new ToolbarButton("Open board from file");
        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.instance.loadBoardFromFile();
            }
        });
        add(openFileButton);
        
        addSeparator();
        
        // Create a button to save the current board to file
        ToolbarButton saveFileButton = new ToolbarButton("Save board to file");
        saveFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.instance.saveCurrentBoardToFile();
            }
        });
        add(saveFileButton);

        addSeparator();

        // Create a button to clear the current board
        ToolbarButton cleanBoardButton = new ToolbarButton("Clear board");
        cleanBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(Main.instance.getFrame(), "The whole board will be deleted!", "Delete board", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if(input != 0) {
                    // 0 means the user picked OK
                    return;
                }
                
                Main.instance.getPinboard().cleanBoard();
                Main.instance.updateFrame();
            }
        });
        add(cleanBoardButton);
        
        addSeparator();
        
        // Create a button to create a new note
        ToolbarButton createNoteButton = new ToolbarButton("Create new note");
        createNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noteTitle = JOptionPane.showInputDialog(Main.instance.getFrame(), "Input Note Title", null);
                
                if(noteTitle == null || noteTitle.trim().length() == 0) {
                    return;
                }
                
                for(Note note : Main.instance.getPinboard().getNoteList()) {
                    if(note.getNoteTitle().equals(noteTitle)) {
                        noteTitle = noteTitle + " (1)";
                    }
                }
                
                Note note = new Note(noteTitle);
                Main.instance.getPinboard().getNoteList().add(note);
                Main.instance.getPinboard().add(note);
                Main.instance.updateFrame();
            }
        });
        add(createNoteButton);
        
        addSeparator();
        
        // Create a button to create a new image note
        ToolbarButton createImageNote = new ToolbarButton("Create image note");
        createImageNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noteTitle = JOptionPane.showInputDialog(Main.instance.getFrame(), "Input Note Title", null);
                
                if(noteTitle == null || noteTitle.trim().length() == 0) {
                    return;
                }
                
                final JFileChooser fileChooser = new JFileChooser(new JFileChooser().getFileSystemView().getDefaultDirectory());
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
                fileChooser.setAcceptAllFileFilterUsed(true);
                
                int returnValue = fileChooser.showOpenDialog(Main.instance.getFrame());
                
                if(returnValue != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                
                File imageFile = fileChooser.getSelectedFile();
                Note note = new Note(noteTitle, Paths.get(imageFile.getAbsolutePath()));
                Main.instance.getPinboard().getNoteList().add(note);
                Main.instance.getPinboard().add(note);
                Main.instance.updateFrame();
            }
        });
        add(createImageNote);
        
        addSeparator();
        
        // Create a button to delete a note from the board
        ToolbarButton deleteNoteButton = new ToolbarButton("Delete existing note");
        deleteNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.instance.deleteNoteFromBoard();
            }
        });
        add(deleteNoteButton);
    }
    
    public class ToolbarButton extends JButton {
        public ToolbarButton(final String title) {
            setText(title);
            
            // Set the text color to black
            setForeground(Color.BLACK);
            
            // Disable effects on button
            setFocusPainted(false);
            setRolloverEnabled(false);
            
            // Set background color of button
            setBackground(new Color(230, 230, 230));
            
            // Sets new bold font
            setFont(new Font("Dialog", Font.BOLD, 14));
        }
    }
}