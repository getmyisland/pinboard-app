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
    public static Main instance;
    
    private final String projectDocumentFilePath = new JFileChooser().getFileSystemView().getDefaultDirectory()
            .toString() + "\\GetMyIsland\\Pinboard";

    private static final JFrame frame = new JFrame();

    private static Toolbar toolbar;
    private static Pinboard pinboard;

    public static void main(String[] args) {
        Main main = new Main();
        
        instance = main;
        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        toolbar = new Toolbar();
        frame.getContentPane().add(toolbar, BorderLayout.PAGE_START);

        pinboard = new Pinboard();
        JScrollPane pinboardScrollPane = new JScrollPane(pinboard);
        pinboardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pinboardScrollPane.setWheelScrollingEnabled(false);
        frame.getContentPane().add(pinboardScrollPane, BorderLayout.CENTER);

        // Detect the screen size and set it to the preferred size
        frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pinboard App");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void updateFrame() {
        frame.revalidate();
        frame.repaint();
    }

    public void loadBoardFromFile() {
        try {
            final File documentDirectory = new File(projectDocumentFilePath + "\\");
            documentDirectory.mkdirs();
            
            final JFileChooser fileChooser = new JFileChooser(documentDirectory);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
            fileChooser.setAcceptAllFileFilterUsed(true);
            
            int returnValue = fileChooser.showOpenDialog(frame);
            if(returnValue != JFileChooser.APPROVE_OPTION) {
                return;
            }
            
            File saveFile = fileChooser.getSelectedFile();
            
            FileReader fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            List<String[]> noteDataLines = new ArrayList<String[]>();
            while((line = br.readLine()) != null) {
               String[] tempArr = line.split(",");
               noteDataLines.add(tempArr);
            }
            pinboard.LoadPinboard(noteDataLines);
            updateFrame();
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveCurrentBoardToFile() {
        try {
            final File documentDirectory = new File(projectDocumentFilePath + "\\");
            documentDirectory.mkdirs();
            final String fileName = JOptionPane.showInputDialog(Main.instance.getFrame(), "Input file name", null);
            final File saveFile = new File(projectDocumentFilePath + "\\" + fileName + ".csv");
            if (!saveFile.exists()) { saveFile.createNewFile(); }
           
            List<String[]> dataLines = new ArrayList<>();
            for(Note note : pinboard.getNoteList()) {
                Point notePoint = note.getLocation();
                
                JTextArea noteArea = note.getNoteDescriptionTextArea();
                String noteImageFilePath = note.getNoteImageFilePath();
                
                if(noteArea != null) {
                    dataLines.add(new String[] {
                            Integer.toString(notePoint.x), Integer.toString(notePoint.y), note.getNoteTitle(), note.getNoteDescriptionTextArea().getText(), "null"      
                    });
                }
                else if (noteImageFilePath != null) {
                    dataLines.add(new String[] {
                            Integer.toString(notePoint.x), Integer.toString(notePoint.y), note.getNoteTitle(), "null", noteImageFilePath    
                    });
                }
            }
            
            PrintWriter pw = new PrintWriter(saveFile);
            
            dataLines.stream()
            .map(this::convertToCSV)
            .forEach(pw::println);
            
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String convertToCSV(String[] data) {
        return Stream.of(data)
          .map(this::escapeSpecialCharacters)
          .collect(Collectors.joining(","));
    }
    
    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Pinboard getPinboard() {
        return pinboard;
    }
}