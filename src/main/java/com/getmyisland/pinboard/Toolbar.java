package com.getmyisland.pinboard;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar {
    /**
     * 
     */
    private static final long serialVersionUID = -1905594052007376798L;

    public Toolbar() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setVgap(10);
        layout.setHgap(10);
        setLayout(layout);
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 218, 218)));
        
        ToolbarButton openFileButton = new ToolbarButton("Open");
        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.instance.loadBoardFromFile();
            }
        });
        add(openFileButton);
        
        addSeparator();
        
        ToolbarButton saveFileButton = new ToolbarButton("Save");
        saveFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.instance.saveCurrentBoardToFile();
            }
        });
        add(saveFileButton);

        addSeparator();

        ToolbarButton cleanBoardButton = new ToolbarButton("New");
        cleanBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getPinboard().CleanBoard();
                Main.instance.UpdateFrame();
            }
        });
        add(cleanBoardButton);
        
        addSeparator();
        
        ToolbarButton createNoteButton = new ToolbarButton("Create Note");
        createNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noteTitle = JOptionPane.showInputDialog(Main.getFrame(), "Input Note Title", null);
                
                if(noteTitle == null || noteTitle.trim().length() == 0) {
                    return;
                }
                
                Note note = new Note(noteTitle);
                Main.getPinboard().getNoteList().add(note);
                Main.getPinboard().add(note);
                Main.instance.UpdateFrame();
            }
        });
        add(createNoteButton);
    }
    
    public class ToolbarButton extends JButton {
        /**
         * 
         */
        private static final long serialVersionUID = 159196000788565220L;

        public ToolbarButton(final String title) {
            setText(title);
            setForeground(Color.BLACK);
            setFocusPainted(false);
            setRolloverEnabled(false);
            setBackground(new Color(230, 230, 230));
            setFont(new Font("Dialog", Font.BOLD, 14));
        }
    }
}