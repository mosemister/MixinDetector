package org.mixindetector.gui.container.filter;

import org.mixindetector.MixinDetectorMain;
import org.mixindetector.MixinFile;
import org.mixindetector.gui.container.CrashContainer;
import org.mixindetector.gui.screen.ResultsScreen;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WithCrashLogFilter implements MixinFilter{

    @Override
    public String getName() {
        return "With Crash";
    }

    @Override
    public boolean shouldKeep(MixinFile file) {
return true;
    }

    @Override
    public void onNew(FilterContainer container) {
        ResultsScreen screen = (ResultsScreen) MixinDetectorMain.getInstance().getFrame().getContentPane();
        TreeSet<String> files = screen.getContainer().getAllFiles().parallelStream().flatMap(file -> file.getMixinFileNames().stream()).collect(Collectors.toCollection(TreeSet::new));

        JDialog dialog = new JDialog();
        dialog.setContentPane(new CrashContainer(files, (selected) -> {
            WithSpecificMixinFileFilter filter = new WithSpecificMixinFileFilter(selected);
            JPanel panel = new JPanel();
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    container.removeFilter(filter);
                }
            });
            panel.add(new JLabel("With Crash File: '" + selected + "'"));
            container.addFilter(filter, panel);
        }));
        dialog.setSize(400,400);
        dialog.setVisible(true);
    }
}
