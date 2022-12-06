package org.mixindetector.gui.container;

import org.mixindetector.MixinFile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MixinFileContainer extends JPanel {

	private final MixinFile file;

	private final JPanel fileNames;
	private final JLabel hasMixinFolder;


	public MixinFileContainer(MixinFile file) {
		this.file = file;
		this.fileNames = new JPanel();
		hasMixinFolder = new JLabel("Has Mixins Folder:");

		init();
	}

	public MixinFile getMixinFile(){
		return this.file;
	}

	private void init() {
		boolean hasMixinFolder = this.file.hasMixinsFolder();
		Color mixinsColour = hasMixinFolder ? Color.GREEN : Color.RED;
		this.setFocusable(true);
		this.setBorder(new LineBorder(mixinsColour));

		this.fileNames.setLayout(new GridLayout(this.file.getMixinFileNames().size(), 1));
		this.file.getMixinFileNames().forEach(name -> this.fileNames.add(new JLabel(name)));

		this.hasMixinFolder.setOpaque(true);
		this.hasMixinFolder.setBackground(mixinsColour);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(5, 0, 0, 0);
		c.fill = GridBagConstraints.BOTH;
		JLabel name = new JLabel(file.getFile().getName());
		name.setBackground(Color.LIGHT_GRAY);
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setShowingFiles(!isShowingFiles());
			}
		});
		add(name, c);
		c.gridy = 1;
		c.weighty = 0;
		add(this.hasMixinFolder, c);
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		add(this.fileNames, c);
		setShowingFiles(false);
	}

	public boolean isShowingFiles() {
		return this.fileNames.isVisible();
	}

	public void setShowingFiles(boolean check) {
		this.fileNames.setVisible(check);
		this.hasMixinFolder.setVisible(check);
	}
}
