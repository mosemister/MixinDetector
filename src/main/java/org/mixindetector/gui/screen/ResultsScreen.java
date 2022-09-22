package org.mixindetector.gui.screen;

import org.mixindetector.MixinFile;
import org.mixindetector.gui.container.ResultsContainer;
import org.mixindetector.gui.container.nav.NavBarContainer;
import org.mixindetector.gui.container.nav.NavButtonContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ResultsScreen extends JPanel {

	private final ResultsContainer container;
	private final NavBarContainer nav;

	public ResultsScreen(Collection<MixinFile> collection) {
		this.nav = new NavBarContainer(new NavButtonContainer("Test", () -> {}));
		this.container = new ResultsContainer(collection);
		init();

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
		c.weighty = 1.0;
		this.add(this.container, c);

	}
}
