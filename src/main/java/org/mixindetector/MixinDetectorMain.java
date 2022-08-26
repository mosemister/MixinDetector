package org.mixindetector;

import org.mixindetector.gui.screen.SelectFolderScreen;

import javax.swing.*;

public class MixinDetectorMain {

    private static MixinDetectorMain instance;
    private final JFrame frame;


    public MixinDetectorMain(JFrame frame){
        this.frame = frame;
        this.init();
    }

    private void init(){
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mixin Detector");
        frame.setContentPane(new SelectFolderScreen());
    }

    public JFrame getFrame(){
        return this.frame;
    }


    public static MixinDetectorMain getInstance(){
    return instance;
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        instance = new MixinDetectorMain(frame);
        frame.setVisible(true);
    }
}
