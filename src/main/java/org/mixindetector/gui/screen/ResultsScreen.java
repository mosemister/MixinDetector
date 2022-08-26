package org.mixindetector.gui.screen;

import org.mixindetector.MixinFile;
import org.mixindetector.gui.container.MixinFileContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ResultsScreen extends JPanel {

    private final Collection<MixinFile> files;

    public ResultsScreen(Collection<MixinFile> files){
        this.files = files;
    init();
    }

    private void init(){
setLayout(new GridLayout(this.files.size(), 1));
this.files.forEach(mf -> add(new MixinFileContainer(mf)));
    }
}
