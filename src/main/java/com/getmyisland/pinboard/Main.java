package com.getmyisland.pinboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
    /** Main instance to access methods from Main. */
    public static final Main instance = new Main();

    /** The file path of the projects directory. */
    private final String projectDocumentFilePath = new JFileChooser().getFileSystemView().getDefaultDirectory()
            .toString() + "\\GetMyIsland\\Pinboard";

    /** The main JFrame component containing the Java application. */
    private final JFrame frame = new JFrame();

    /** The toolbar component. */
    private final Toolbar toolbar = new Toolbar();

    /** The pinboard component. */
    private final Pinboard pinboard = new Pinboard();

    public static void main(String[] args) {
        instance.frame.getContentPane().setLayout(new BorderLayout());
        instance.frame.getContentPane().setBackground(new Color(255, 255, 255));

        instance.frame.getContentPane().add(instance.toolbar, BorderLayout.PAGE_START);

        // Create a ScrollPane with the Pinboard component and add it to the frame
        JScrollPane pinboardScrollPane = new JScrollPane(instance.pinboard);
        pinboardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setWheelScrollingEnabled(false);
        instance.frame.getContentPane().add(pinboardScrollPane, BorderLayout.CENTER);

        // Detect the screen size and set it to the preferred size
        instance.frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Application Settings
        instance.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance.frame.setTitle("Pinboard Creator");
        instance.frame.pack();
        instance.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        instance.frame.setResizable(true);
        instance.frame.setVisible(true);
    }

    /**
     * Updates the JFrame, by calling {@link javax.swing.JFrame#revalidate()} and
     * {@link javax.swing.JFrame#repaint()}
     */
    public void updateFrame() {
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Reads a user-chosen {@code .csv file} and turns every line into a String[].
     * One Array contains the necessary information for one Note.
     */
    public void loadBoardFromFile() {
        try {
            final File documentDirectory = new File(projectDocumentFilePath + "\\");
            // Creates the document directory if it doesn't exist
            documentDirectory.mkdirs();

            // Let the user choose a file
            final JFileChooser fileChooser = new JFileChooser(documentDirectory);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
            fileChooser.setAcceptAllFileFilterUsed(true);

            // Check if the user selected a valid file
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue != JFileChooser.APPROVE_OPTION) {
                return;
            }

            // Create a file from the users selected file
            File saveFile = fileChooser.getSelectedFile();

            // Read the file
            FileReader fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);
            
            String line = "";
            List<String[]> noteDataLines = new ArrayList<>();
            
            while ((line = br.readLine()) != null) {
                // Splits the string at "," and puts each part into an array
                String[] tempArr = line.split(",");
                noteDataLines.add(tempArr);
            }

            // Load the pinboard with the String[]
            pinboard.loadPinboard(noteDataLines);

            // Update the frame to display the changes
            updateFrame();

            // Close the File- and BufferedReader
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the current board to a {@code .csv file}.
     */
    public void saveCurrentBoardToFile() {
        try {
            final File documentDirectory = new File(projectDocumentFilePath + "\\");
            // Check if project directory exists and if not create it
            documentDirectory.mkdirs();

            // Let the user choose a file name
            final String fileName = JOptionPane.showInputDialog(Main.instance.getFrame(), "Input file name", null);

            // Create a new file from the users selected file name
            final File saveFile = new File(projectDocumentFilePath + "\\" + fileName + ".csv");
            if (!saveFile.exists()) {
                // Create a file if it not already exists
                saveFile.createNewFile();
            }

            List<String[]> dataLines = new ArrayList<>();
            for (Note note : pinboard.getNoteList()) {
                // Store the location of the note
                Point notePoint = note.getLocation();

                // Store the description
                JTextArea noteArea = note.getNoteDescriptionTextArea();

                // Store the file path of the image
                String noteImageFilePath = note.getNoteImageFilePath();

                if (noteArea != null) {
                    // If is text note
                    dataLines.add(new String[] { Integer.toString(notePoint.x), Integer.toString(notePoint.y),
                            note.getNoteTitle(), note.getNoteDescriptionTextArea().getText(), "null" });
                } else if (noteImageFilePath != null) {
                    // If is image note
                    dataLines.add(new String[] { Integer.toString(notePoint.x), Integer.toString(notePoint.y),
                            note.getNoteTitle(), "null", noteImageFilePath });
                }
            }

            // Convert the lines to CSV and write it to file
            PrintWriter pw = new PrintWriter(saveFile);
            dataLines.stream().map(this::convertToCSV).forEach(pw::println);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a string array of data into a single string in CSV style.
     * 
     * @param data
     * @return
     */
    public String convertToCSV(String[] data) {
        return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
    }

    /**
     * Removes all special characters from a string.
     * 
     * @param data
     * @return
     */
    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    /**
     * Removes a user-chosen Note from the board.
     */
    public void deleteNoteFromBoard() {
        List<String> optionsList = new ArrayList<>();

        List<Note> noteList = pinboard.getNoteList();
        if (noteList == null || noteList.isEmpty()) {
            // Return if there are no notes in the note list
            return;
        }

        for (Note note : noteList) {
            // Add the note title to the option list
            optionsList.add(note.getNoteTitle());
        }

        // Create an array from all the options
        String[] options = optionsList.stream().toArray(String[]::new);

        // Let the user choose a note from all the notes on the board
        String selectedNoteTitle = JOptionPane.showInputDialog(frame, "Choose the note you want to delete",
                "Delete Note Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]).toString();

        // Get the note by name
        Note selectedNote = pinboard.getNoteByName(selectedNoteTitle);
        if (selectedNote != null) {
            // Delete the selected note
            pinboard.deleteNote(selectedNote);
        } else {
            System.out.println("Selected Note is null");
        }
    }

    /** Get the {@link #frame} */
    public JFrame getFrame() {
        return frame;
    }

    /** Get the {@link #pinboard} */
    public Pinboard getPinboard() {
        return pinboard;
    }
}