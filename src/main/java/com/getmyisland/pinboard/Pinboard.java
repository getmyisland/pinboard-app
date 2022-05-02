package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Pinboard extends JPanel {
    /** List of {@code notes} to keep track of the current notes on the board */
    private List<Note> noteList = new ArrayList<>();
    
    public Pinboard() {
        // Set a new background color
        setBackground(new Color(255, 255, 255));
        
        // Sets the preferred size
        setPreferredSize(new Dimension(2500, 2500));
        
        // Sets the layout
        setLayout(new DragLayout());
    }
    
    /**
     * Removes all notes from the current board.
     */
    public void CleanBoard() {
        for (Note note : noteList) {
            // Remove each note
            remove(note);
        }
        
        // Create a new list to get rid of the old one
        noteList = new ArrayList<Note>();
    }
    
    /**
     * Loads a pinboard from a {@code .csv} file. Cycles trough {@code noteDataLines} and adds new notes to the board. 1 String[] = 1 Note.
     * 
     * @param noteDataLines a List of String[] to pass in the note data from the save file.
     */
    public void LoadPinboard(List<String[]> noteDataLines) {
        CleanBoard();
        
        // Cycle trough all String[]
        for(String[] noteData : noteDataLines) {
            Note newNote = null;
            
            if(!noteData[3].equals("null")) {
                // Note is a text note
                newNote = new Note(noteData[2]);
                newNote.getNoteDescriptionTextArea().setText(noteData[3]);
            } else if (!noteData[4].equals("null")) {
                // Note is a image note
                newNote = new Note(noteData[2], noteData[4]);
            }
            
            if(newNote == null) {
                newNote = new Note("New Note");
            }
            
            // Set the location of the note on the board
            newNote.setLocation(Integer.parseInt(noteData[0]), Integer.parseInt(noteData[1]));
            
            // Add the note to the board and to the note list
            noteList.add(newNote);
            add(newNote);
        }
    }
    
    /**
     * Get the list of notes.
     * 
     * @return {@link #noteList}
     */
    public List<Note> getNoteList(){
        return noteList;
    }
}