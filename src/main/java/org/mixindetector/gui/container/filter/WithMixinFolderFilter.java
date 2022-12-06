package org.mixindetector.gui.container.filter;

import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WithMixinFolderFilter implements MixinFilter{
    @Override
    public String getName() {
        return "With Mixin Folder";
    }

    @Override
    public boolean shouldKeep(MixinFile file) {
        return file.hasMixinsFolder();
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
                container.removeFilter(WithMixinFolderFilter.this);
            }
        });
        panel.add(new JLabel("With Mixins Folder"));
        container.addFilter(this, panel);
        container.repaint();
        container.revalidate();
    }
}
