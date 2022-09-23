package org.mixindetector.gui.screen;

import org.mixindetector.MixinFile;
import org.mixindetector.gui.container.ResultsContainer;
import org.mixindetector.gui.container.nav.NavBarContainer;
import org.mixindetector.gui.container.nav.NavButtonContainer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collection;
import java.util.function.Predicate;

public class ResultsScreen extends JPanel {

	private final ResultsContainer container;
	private final NavBarContainer nav;
	private final File folder;

	public ResultsScreen(File folder, Collection<MixinFile> collection) {
		this.folder = folder;
		this.nav =
				new NavBarContainer(
						new NavButtonContainer("With File", () -> {
							String fileName =
									JOptionPane.showInputDialog(ResultsScreen.this, "File name").toLowerCase();
							setFilter(file -> file.getMixinFileNames()
									.parallelStream()
									.anyMatch(name -> name.toLowerCase().startsWith(fileName)));
						}),
						new NavButtonContainer("Any Mixins", () -> setFilter(ResultsContainer.ANY_MIXIN)));
		this.container = new ResultsContainer(collection);
		init();

	}

	private void setFilter(Predicate<MixinFile> predicate) {
		this.container.setFilters(predicate);
		this.container.repaint();
		this.container.revalidate();
	}

	private void init() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		this.add(this.nav, c);
		c.gridy = 1;
		this.add(new JLabel("Found " + this.container.getAllFiles().size() + " mods"), c);
		c.gridy = 2;
		this.add(new JLabel("Found "
				+ this.container.getAllFiles().parallelStream().filter(ResultsContainer.ANY_MIXIN).count()
				+ " with mixins"), c);
		c.gridy = 3;
		this.add(new JLabel("In " + this.folder.getPath()), c);
		c.gridy = 4;
		c.weighty = 1.0;
		this.add(new JScrollPane(this.container), c);

	}
}
