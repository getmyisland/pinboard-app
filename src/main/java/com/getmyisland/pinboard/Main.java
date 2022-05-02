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

    /** String containing the file path of the projects directory. */
    private final String projectDocumentFilePath = new JFileChooser().getFileSystemView().getDefaultDirectory()
            .toString() + "\\GetMyIsland\\Pinboard";

    /** The main JFrame. */
    private static final JFrame frame = new JFrame();

    /** The toolbar. */
    private static Toolbar toolbar;

    /** The pinboard. */
    private static Pinboard pinboard;

    public static void main(String[] args) {
        // Add a layout to the frame
        frame.getContentPane().setLayout(new BorderLayout());
        // Set a new background color for the frame
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        // Create a new toolbar and add it to the frame
        toolbar = new Toolbar();
        frame.getContentPane().add(toolbar, BorderLayout.PAGE_START);

        // Create a new pinboard and add it to the frame
        pinboard = new Pinboard();
        JScrollPane pinboardScrollPane = new JScrollPane(pinboard);
        pinboardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setWheelScrollingEnabled(false);
        frame.getContentPane().add(pinboardScrollPane, BorderLayout.CENTER);

        // Detect the screen size and set it to the preferred size
        frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Application Settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pinboard App");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Method to update the JFrame.
     */
    public void updateFrame() {
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Reads a selected {@code .csv file} and creates a List of String[]. 1 String[]
     * = 1 Line in the file. The String[] contains all necessary information to
     * create a new note, like title, description or image file path.
     */
    public void loadBoardFromFile() {
        try {
            // Check if the document directory even exists
            final File documentDirectory = new File(projectDocumentFilePath + "\\");
            documentDirectory.mkdirs();

            // Let the user choose a file he wants to read
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
            List<String[]> noteDataLines = new ArrayList<String[]>();
            while ((line = br.readLine()) != null) {
                String[] tempArr = line.split(",");
                noteDataLines.add(tempArr);
            }

            // Load the pinboard
            pinboard.LoadPinboard(noteDataLines);

            // Update the frame to display the changes
            updateFrame();

            // Close the File- and BufferedReader
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the current board to file.
     */
    public void saveCurrentBoardToFile() {
        try {
            // Check if project directory even exists
            final File documentDirectory = new File(projectDocumentFilePath + "\\");
            documentDirectory.mkdirs();

            // Let the user choose a file name
            final String fileName = JOptionPane.showInputDialog(Main.instance.getFrame(), "Input file name", null);

            // Create a new file from the users selected file name
            final File saveFile = new File(projectDocumentFilePath + "\\" + fileName + ".csv");
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            } // Create a file if it not already exists

            List<String[]> dataLines = new ArrayList<>();
            for (Note note : pinboard.getNoteList()) {
                // Store the location of the note
                Point notePoint = note.getLocation();

                // Store the description
                JTextArea noteArea = note.getNoteDescriptionTextArea();

                // Store the file path of the image
                String noteImageFilePath = note.getNoteImageFilePath();

                if (noteArea != null) {
                    // If text note
                    dataLines.add(new String[] { Integer.toString(notePoint.x), Integer.toString(notePoint.y),
                            note.getNoteTitle(), note.getNoteDescriptionTextArea().getText(), "null" });
                } else if (noteImageFilePath != null) {
                    // If image note
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
     * Converts the string array of data to a single string in CSV style.
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
     * Removes a user-chosen note from the board.
     */
    public void DeleteNoteFromBoard() {
        List<Note> options = pinboard.getNoteList();
        List<String> optionStrings = new ArrayList<>();
        for(Note note : options) {
            
        }
        //Object selectedNote = JOptionPane.showInputDialog(frame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        //System.out.println(selectedNote.toString());
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