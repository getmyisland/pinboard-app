package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Pinboard extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -43371314411023743L;
    private List<Note> noteList = new ArrayList<>();
    
    public Pinboard() {
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(2500, 2500));
        setLayout(new DragLayout());
    }
    
    public void CleanBoard() {
        for (Note note : noteList) {
            remove(note);
        }
        
        noteList = new ArrayList<Note>();
    }
    
    public void LoadPinboard(List<String[]> noteDataLines) {
        CleanBoard();
        
        for(String[] noteData : noteDataLines) {
            Note newNote = null;
            
            if(!noteData[3].equals("null")) {
                newNote = new Note(noteData[2]);
                newNote.getNoteDescriptionTextArea().setText(noteData[3]);
            } else if (!noteData[4].equals("null")) {
                newNote = new Note(noteData[2], noteData[4]);
            }
            
            if(newNote == null) {
                newNote = new Note("New Note");
            }
            
            newNote.setLocation(Integer.parseInt(noteData[0]), Integer.parseInt(noteData[1]));
            noteList.add(newNote);
            add(newNote);
        }
    }
    
    public List<Note> getNoteList(){
        return noteList;
    }
}