package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.Dimension;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.getmyisland.pinboard.Note.NoteType;

public class Pinboard extends JPanel {
    /** List of {@code notes} to keep track of the current notes on the board */
    private List<Note> noteList = new ArrayList<>();

    public Pinboard() {
        // Set a new background color
        setBackground(new Color(255, 255, 255));

        // Sets the preferred size
        setPreferredSize(new Dimension(4000, 4000));

        // Sets the layout
        setLayout(new DragLayout());
    }

    /**
     * Removes all notes from the current board.
     */
    public void cleanBoard() {
        for (Note note : noteList) {
            // Remove each note
            remove(note);
        }

        // Create a new list to get rid of the old one
        noteList = new ArrayList<Note>();
    }

    /**
     * Loads a pinboard from a {@code .csv} file. Cycles trough
     * {@code noteDataLines} and adds new notes to the board. 1 String[] = 1 Note.
     * 
     * @param noteDataLines a List of String[] to pass in the note data from the
     *                      save file.
     */
    public void loadPinboard(List<String[]> noteDataLines) {
        cleanBoard();

        // Cycle trough all String[]
        for (String[] noteData : noteDataLines) {
            Note newNote = null;

            // Get the NoteType
            NoteType newNoteType = NoteType.valueOf(noteData[2]);

            switch (newNoteType) {
            case TitleDescriptionNote:
                newNote = new Note(noteData[3]);
                newNote.getNoteDescriptionTextArea().setText(noteData[4]);
                break;
            case TitleImageNote:
                newNote = new Note(noteData[3], Paths.get(noteData[4]));
                break;
            case ImageNote:
                newNote = new Note(Paths.get(noteData[3]));
                break;
            default:
                newNote = new Note("Note Title");
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
    public List<Note> getNoteList() {
        return noteList;
    }

    /**
     * Get a note from the {@link #noteList} by name.
     * 
     * @param name is the name you are searching for.
     * @return
     */
    public Note getNoteByName(final String name) {
        for (Note note : noteList) {
            if (note.getNoteTitle().equals(name)) {
                // If the notes name is the same as the passed in name
                return note;
            }
        }

        return null;
    }

    /**
     * Delete a note.
     * 
     * @param note is the note you want to delete.
     */
    public void deleteNote(final Note note) {
        noteList.remove(note);
        remove(note);
        Main.instance.updateFrame();
    }
}