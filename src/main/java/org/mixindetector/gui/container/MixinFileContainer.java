package org.mixindetector.gui.container;

import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.*;

public class MixinFileContainer extends JPanel {

	private final MixinFile file;

	private final JPanel fileNames;

	public MixinFileContainer(MixinFile file) {
		this.file = file;
		this.fileNames = new JPanel();
		init();
	}

	private void init() {
		this.setFocusable(true);

		this.fileNames.setLayout(new GridLayout(this.file.getMixinFileNames().size(), 1));
		this.file.getMixinFileNames().forEach(name -> this.fileNames.add(new JLabel(name)));

		boolean hasMixinFolder = this.file.hasMixinsFolder();
		JLabel hasMixinFolderLabel = new JLabel("Has Mixins Folder:");
		hasMixinFolderLabel.setOpaque(true);
		hasMixinFolderLabel.setBackground(hasMixinFolder ? Color.GREEN : Color.RED);


		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		add(new JLabel(file.getFile().getName()));
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		add(hasMixinFolderLabel, c);
		c.gridy = 2;
		c.weighty = 1.0;
		add(this.fileNames, c);
	}

	public boolean isShowingFiles() {
		return this.fileNames.isVisible();
	}

	public void setShowingFiles(boolean check) {
		this.fileNames.setVisible(check);
	}
}
