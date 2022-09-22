package org.mixindetector.gui.container;

import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.*;

public class MixinFileContainer extends JPanel {

	private final MixinFile file;

	public MixinFileContainer(MixinFile file) {
		this.file = file;
		init();
	}

	private void init() {
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
	}
}
