package org.mixindetector.gui.screen;

import org.mixindetector.MixinFile;
import org.mixindetector.gui.container.ResultsContainer;
import org.mixindetector.gui.container.filter.MixinFilters;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collection;

public class ResultsScreen extends JPanel {

	private final ResultsContainer container;
	private final File folder;

	public ResultsScreen(File folder, Collection<MixinFile> collection) {
		this.folder = folder;
		this.container = new ResultsContainer(collection);
		init();

	}

	public File getFolder(){
		return this.folder;
	}

	public ResultsContainer getContainer(){
		return this.container;
	}

	private void init() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		this.add(new JLabel("Found " + this.container.getAllFiles().size() + " mods"), c);
		c.gridy = 1;
		this.add(new JLabel("Found "
				+ this.container
				.getAllFiles()
				.parallelStream()
				.filter(file -> MixinFilters.WITH_FILES.shouldKeep(file) || MixinFilters.WITH_FOLDER.shouldKeep(file))
				.count()
				+ " with mixins"), c);
		c.gridy = 2;
		this.add(new JLabel("In " + this.folder.getPath()), c);
		c.gridy = 3;
		c.weighty = 1.0;
		this.add(this.container, c);

	}
}
