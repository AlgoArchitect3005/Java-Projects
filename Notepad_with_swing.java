import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad_with_swing extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    boolean wordWrap = false;
    String currentFilePath = null;

    public Notepad_with_swing() {
        // Frame setup
        setTitle("Mini Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));

        // Scroll bar
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save As");
        JMenuItem exit = new JMenuItem("Exit");

        // Keyboard shortcuts for file operations ( new, open, save, save as, exit )
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveAsFile.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        saveAsFile.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        fileMenu.add(exit);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");

        // Keyboard shortcuts for cut, copy, paste
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        // Format Menu
        JMenu formatMenu = new JMenu("Format");
        JMenuItem wrap = new JMenuItem("Word Wrap");
        
        // Keyboard shortcut for word wrap
        wrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        
        wrap.addActionListener(this);
        formatMenu.add(wrap);

        // Add to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        setVisible(true);
    }

    // FILE OPERATIONS

    private void newFile() {
        textArea.setText("");
        currentFilePath = null;
        setTitle("Mini Notepad - New File");
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            currentFilePath = file.getAbsolutePath();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                textArea.setText("");
                String line;

                while ((line = br.readLine()) != null) {
                    textArea.append(line + "\n");
                }

                setTitle("Mini Notepad - " + file.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error reading file!");
            }
        }
    }

    private void saveFile() {
        // If no file selected previously → Save As
        if (currentFilePath == null) {
            saveAsFile();
            return;
        }

        try (FileWriter fw = new FileWriter(currentFilePath)) {
            fw.write(textArea.getText());
            JOptionPane.showMessageDialog(this, "File Saved!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file!");
        }
    }

    private void saveAsFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            currentFilePath = file.getAbsolutePath();

            try (FileWriter fw = new FileWriter(file)) {
                fw.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File Saved!");
                setTitle("Mini Notepad - " + file.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving file!");
            }
        }
    }

    // Action Handler
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                newFile();
                break;

            case "Open":
                openFile();
                break;

            case "Save":
                saveFile();
                break;

            case "Save As":
                saveAsFile();
                break;

            case "Exit":
                System.exit(0);
                break;

            case "Cut":
                textArea.cut();
                break;

            case "Copy":
                textArea.copy();
                break;

            case "Paste":
                textArea.paste();
                break;

            case "Word Wrap":
                wordWrap = !wordWrap;
                textArea.setLineWrap(wordWrap);
                textArea.setWrapStyleWord(wordWrap);
                String msg = wordWrap ? "Word Wrap Enabled" : "Word Wrap Disabled";
                JOptionPane.showMessageDialog(this, msg);
                break;
        }
    }

    public static void main(String[] args) {
        new Notepad_with_swing();
    }
}
