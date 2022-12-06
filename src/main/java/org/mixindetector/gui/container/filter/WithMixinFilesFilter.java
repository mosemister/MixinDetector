package org.mixindetector.gui.container.filter;

import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WithMixinFilesFilter implements MixinFilter{
    @Override
    public String getName() {
        return "With Mixin Files";
    }

    @Override
    public boolean shouldKeep(MixinFile file) {
        return !file.getMixinFileNames().isEmpty();
    }

    @Override
    public void onNew(FilterContainer container) {
if(container.hasFilter(this)){
    return;
}
        JPanel panel = new JPanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                container.removeFilter(WithMixinFilesFilter.this);
            }
        });
        panel.add(new JLabel("With Mixins Files"));
        container.addFilter(this, panel);
        container.repaint();
        container.revalidate();
    }
}
