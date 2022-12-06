package org.mixindetector.gui.screen;

import org.mixindetector.MixinDetectorMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class SelectFolderScreen extends JPanel {

    private final JTextField folderPath;
    private final JButton selectFolderButton;
    private final JButton okButton;

    public SelectFolderScreen() {
        this.folderPath = new JTextField();
        this.okButton = new JButton("Ok");
        this.selectFolderButton = new JButton("...");
        this.init();
    }

    private void init() {
        this.selectFolderButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            while (true) {
                int result = chooser.showOpenDialog(SelectFolderScreen.this);
                if (result == JFileChooser.CANCEL_OPTION) {
                    break;
                }
                if (result != JFileChooser.APPROVE_OPTION) {
                    continue;
                }
                File file = chooser.getSelectedFile();
                if (!file.isDirectory()) {
                    JOptionPane.showMessageDialog(chooser, "The selected folder must be a folder", "error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                File[] potentialMods = file.listFiles(pathname -> pathname.getName().endsWith(".jar"));
                if (potentialMods == null || potentialMods.length == 0) {
                    JOptionPane.showMessageDialog(chooser, "No mod files found", "error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                SelectFolderScreen.this.folderPath.setText(file.getAbsolutePath());
                this.okButton.setEnabled(true);
                break;
            }
        });

        this.folderPath.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                new Thread(() -> okButton.setEnabled(folderPath.getText().replaceAll(" ", "").replaceAll("\t", "").length() != 0)).start();
                super.keyReleased(e);
            }
        });

        this.okButton.addActionListener((e) -> {
            File modsFolder = new File(this.folderPath.getText());
            if(!modsFolder.exists()){
                JOptionPane.showMessageDialog(SelectFolderScreen.this, "Folder cannot be found", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!modsFolder.isDirectory()){
                JOptionPane.showMessageDialog(SelectFolderScreen.this, "Folder is not a valid folder", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            File[] files = modsFolder.listFiles(pathname -> pathname.getName().endsWith(".jar"));

            if(files == null || files.length == 0){
                JOptionPane.showMessageDialog(SelectFolderScreen.this, "Folder does not contain any mods", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            JFrame frame = MixinDetectorMain.getInstance().getFrame();
            frame.setContentPane(new ScanningScreen(modsFolder));
            frame.revalidate();
            frame.repaint();

        });

        okButton.setEnabled(false);


        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 0;
        c.gridwidth = 2;
        add(new JLabel("Select your forge mods folder"), c);
        c.gridy = 1;
        c.gridwidth = 1;
        add(this.folderPath, c);
        c.weightx = 0;
        c.gridx = 1;
        add(this.selectFolderButton, c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.weighty = 1.0;
        add(new JPanel(), c);
        c.gridy = 3;
        c.weighty = 0;
        add(this.okButton, c);
    }
}
