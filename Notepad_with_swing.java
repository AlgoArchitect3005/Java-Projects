import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.Preferences;

public class Notepad_with_swing extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JPanel statusBar;
    JLabel statusLabel;
    boolean wordWrap = false;
    boolean darkMode = false;
    String currentFilePath = null;

    Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    GradientPanel gradientPanel;

    public Notepad_with_swing() {

        // ===== Frame =====
        setTitle("NoteNest: A cozy space for all your thoughts and ideas");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Text Area =====
        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 18));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        // ===== ScrollPane =====
        scrollPane = new JScrollPane(textArea);

        // ===== Gradient Panel (for dark mode background) =====
        gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(scrollPane, BorderLayout.CENTER);

        add(gradientPanel, BorderLayout.CENTER);

        // ===== Status Bar =====
        statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("Ln 1, Col 1 | Words: 0");
        statusBar.add(statusLabel, BorderLayout.WEST);

        add(statusBar, BorderLayout.SOUTH);

        // ===== Menu Bar =====
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // -------- File Menu --------
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save As");
        JMenuItem exit = new JMenuItem("Exit");

        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
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

        // -------- Edit Menu --------
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");

        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        // -------- Format Menu --------
        JMenu formatMenu = new JMenu("Format");
        JMenuItem wrap = new JMenuItem("Word Wrap");
        wrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        wrap.addActionListener(this);
        formatMenu.add(wrap);

        // -------- View Menu --------
        JMenu viewMenu = new JMenu("View");
        JMenuItem darkModeItem = new JMenuItem("Toggle Dark Mode");
        darkModeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        darkModeItem.addActionListener(this);
        viewMenu.add(darkModeItem);

        // ===== Add menus =====
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);

        // ===== Load last opened file =====
        loadLastOpenedFile();

        // ===== Status Bar =====
         textArea.addCaretListener(e->updateStatusBar());
        setVisible(true);
    }

    // ================= FILE OPERATIONS =================

    private void newFile() {
        textArea.setText("");
        currentFilePath = null;
        setTitle("NoteNest - New File");
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            currentFilePath = file.getAbsolutePath();
            prefs.put("lastFilePath", currentFilePath);

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                textArea.setText("");
                String line;
                while ((line = br.readLine()) != null) {
                    textArea.append(line + "\n");
                }
                setTitle("NoteNest - " + file.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening file");
            }
        }
    }

    private void saveFile() {
        if (currentFilePath == null) {
            saveAsFile();
            return;
        }

        try (FileWriter fw = new FileWriter(currentFilePath)) {
            fw.write(textArea.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file");
        }
    }

    private void saveAsFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            currentFilePath = file.getAbsolutePath();
            prefs.put("lastFilePath", currentFilePath);

            try (FileWriter fw = new FileWriter(file)) {
                fw.write(textArea.getText());
                setTitle("NoteNest - " + file.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving file");
            }
        }
    }

    // ================= ACTION HANDLER =================

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New" -> newFile();
            case "Open" -> openFile();
            case "Save" -> saveFile();
            case "Save As" -> saveAsFile();
            case "Exit" -> System.exit(0);
            case "Cut" -> textArea.cut();
            case "Copy" -> textArea.copy();
            case "Paste" -> textArea.paste();
            case "Word Wrap" -> {
                wordWrap = !wordWrap;
                textArea.setLineWrap(wordWrap);
                textArea.setWrapStyleWord(wordWrap);
                String msg = wordWrap ? "Word Wrap Enabled" : "Word Wrap Disabled";
                JOptionPane.showMessageDialog(this, msg);
            }
            case "Toggle Dark Mode" -> toggleDarkMode();
        }
    }

    // ================= DARK MODE =================

    private void toggleDarkMode() {
        darkMode = !darkMode;

        if (darkMode) {
            textArea.setBackground(new Color(40, 44, 52));
            textArea.setForeground(Color.WHITE);
            textArea.setCaretColor(Color.WHITE);
            scrollPane.getViewport().setOpaque(false);
            
            //====Status Bar Dark Mode====
            statusBar.setBackground(new Color(28, 31, 36));
            statusLabel.setForeground(Color.WHITE);

            menuBar.setBackground(new Color(45,44,68));
            for (MenuElement m : menuBar.getSubElements()) {
                ((JMenu) m.getComponent()).setForeground(Color.WHITE);
            }

        } else {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
            textArea.setCaretColor(Color.BLACK);
            scrollPane.getViewport().setOpaque(true);
 
            //====Status Bar Light Mode====
            statusBar.setBackground(UIManager.getColor("Panel.background"));
            statusLabel.setForeground(Color.BLACK);
            menuBar.setBackground(UIManager.getColor("MenuBar.background"));
            for (MenuElement m : menuBar.getSubElements()) {
                ((JMenu) m.getComponent()).setForeground(Color.BLACK);
            }
        }

        repaint();
    }

    // ================= LAST FILE =================

    private void loadLastOpenedFile() {
        String path = prefs.get("lastFilePath", null);
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    textArea.setText("");
                    String line;
                    while ((line = br.readLine()) != null) {
                        textArea.append(line + "\n");
                    }
                    currentFilePath = path;
                    setTitle("NoteNest - " + file.getName());
                } catch (Exception ignored) {
                }
            }
        }
    }

    // ================= STATUS BAR =================
    private void updateStatusBar(){
        try{
        int caretPos = textArea.getCaretPosition();
        int line = textArea.getLineOfOffset(caretPos);
        int col = caretPos - textArea.getLineStartOffset(line);
        String text = textArea.getText().trim();
        int words = text.isEmpty() ? 0 : text.split("\\s+").length;
        statusLabel.setText("Ln " + (line + 1) + ", Col " + (col + 1) + " | Words: " + words);
    }
    catch(Exception e){
        e.printStackTrace();
}
}
    

    public static void main(String[] args) {
        new Notepad_with_swing();
    }
}

// ================= GRADIENT PANEL =================

class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(40, 44, 52),
                0, getHeight(), new Color(28, 31, 36));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
