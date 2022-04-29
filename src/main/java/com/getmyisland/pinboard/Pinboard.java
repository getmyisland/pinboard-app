package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Pinboard extends JPanel {
    private List<Note> noteList = new ArrayList<>();
    
    public Pinboard() {
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(2500, 2500));
        setLayout(new DragLayout());
    }
    
    public void LoadPinboard(List<String[]> noteDataLines) {
        for (Note note : noteList) {
            remove(note);
        }
        
        noteList = new ArrayList<Note>();
        
        for(String[] noteData : noteDataLines) {
            Note newNote = new Note(noteData[2]);
            newNote.getNoteDescriptionTextArea().setText(noteData[3]);
            newNote.setLocation(Integer.parseInt(noteData[0]), Integer.parseInt(noteData[1]));
            noteList.add(newNote);
            add(newNote);
        }
    }
    
    public List<Note> getNoteList(){
        return noteList;
    }
}