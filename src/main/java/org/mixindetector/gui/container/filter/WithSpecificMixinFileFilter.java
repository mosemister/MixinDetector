package org.mixindetector.gui.container.filter;

import org.mixindetector.MixinDetectorMain;
import org.mixindetector.MixinFile;
import org.mixindetector.gui.screen.ResultsScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WithSpecificMixinFileFilter implements MixinFilter {

    private final String fileName;

    public WithSpecificMixinFileFilter(){
        this(null);
    }

    public WithSpecificMixinFileFilter(String fileName){
        this.fileName = fileName;
    }
    @Override
    public String getName() {
        return "Specific File";
    }

    @Override
    public boolean shouldKeep(MixinFile file) {
        if(this.fileName == null){
            return false;
        }
        return file.getMixinFileNames().parallelStream().anyMatch(fileName -> fileName.toLowerCase().startsWith(this.fileName.toLowerCase()));
    }

    @Override
    public void onNew(FilterContainer container) {
        ResultsScreen screen = (ResultsScreen) MixinDetectorMain.getInstance().getFrame().getContentPane();
        TreeSet<String> files = screen.getContainer().getAllFiles().parallelStream().flatMap(file -> file.getMixinFileNames().stream()).collect(Collectors.toCollection(TreeSet::new));

        JComboBox<String> comboBox = new JComboBox<>();
        files.forEach(comboBox::addItem);

        Object result = JOptionPane.showInputDialog(null, "File to filter to", "Filter", JOptionPane.QUESTION_MESSAGE, null, files.toArray(), files.first());
if(result == null){
    return;
}

        WithSpecificMixinFileFilter filter = new WithSpecificMixinFileFilter(result.toString());
        JPanel panel = new JPanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                container.removeFilter(filter);
            }
        });
        panel.add(new JLabel("With File: '" + result + "'"));
        container.addFilter(this, panel);


        container.addFilter(filter, panel);
    }
}
