package org.mixindetector.gui.container;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CrashContainer extends JPanel {

    private final JTextArea crashReport;
    private final JPanel results;
    private final Consumer<String> onSelect;
    private final Collection<String> mixinFiles = new LinkedList<>();

    public CrashContainer(Collection<String> mixinFiles, Consumer<String> onSelect){
        this.crashReport = new JTextArea();
        this.results = new JPanel();
        this.onSelect = onSelect;
        this.mixinFiles.addAll(mixinFiles);
        init();
    }

    private void init(){
        this.crashReport.setEditable(true);
        this.crashReport.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                new Thread(() -> {
                    onTextEntered();
                }).start();
            }
        });
        this.crashReport.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Crash report"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.7;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(this.crashReport), c);
        c.gridx = 1;
        c.weightx = 0.3;
        add(new JScrollPane(this.results), c);
    }

    private void onTextEntered(){
        Arrays.stream(this.results.getComponents()).filter(com -> com instanceof JPanel).forEach(this.results::remove);

        String text = this.crashReport.getText();
        List<String> mixinFile = new LinkedList<>();

        int start = 0;
        for(int end = 0; end < text.length(); end++){
            String subString = text.substring(start, end);
            if (subString.endsWith(".json")){
                Optional<String> opFile = this.mixinFiles.parallelStream().filter(file -> subString.toLowerCase().endsWith(file.toLowerCase())).findAny();
                if(!opFile.isPresent()){
                    continue;
                }
                String file = opFile.get();

                if(mixinFile.contains(file)){
                    start = end + 1;
                    continue;
                }
                int before = Math.max(start - 4, 0);
                if (text.substring(before, end).startsWith("APP:")){
                    //the app: shows all mixins, ignore this
                    continue;
                }

                int max = Math.min(end + 10, text.length() - 1);
                int min = Math.max(start - 10, 0);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 1));
                panel.add(new JLabel(file));
                panel.add(new JLabel(text.substring(min, max)));
                panel.setBorder(new EtchedBorder());
                this.results.add(panel);
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
onSelect.accept(file);
                    }
                });
                mixinFile.add(file);

    start = end + 1;
    continue;
            }
            char at = text.charAt(end);
            if(at == ' ' || at == ':'){
                start = end + 1;
            }
        }
        if(mixinFile.isEmpty()){
            return;
        }
        this.results.repaint();
        this.results.revalidate();
        this.results.setLayout(new GridLayout(mixinFile.size(), 0));
    }
}
