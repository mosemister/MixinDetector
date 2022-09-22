package org.mixindetector.gui.container.nav;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

public class NavBarContainer extends JPanel {

	private final Collection<NavButtonContainer> navButton =
			new TreeSet<>(Comparator.comparing(NavButtonContainer::getText));

	@Deprecated
	public NavBarContainer() {
		this(new NavButtonContainer[0]);
	}

	public NavBarContainer(NavButtonContainer... containers) {
		this(Arrays.asList(containers));
	}

	public NavBarContainer(Collection<NavButtonContainer> containers) {
		this.navButton.addAll(containers);
		init();
	}

	private void init() {
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.LIGHT_GRAY);
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		for (NavButtonContainer container : this.navButton) {
			add(container, c);
			c.gridx = c.gridx + 1;
		}

		c.gridx = c.gridx + 1;
		c.weightx = 1.0;
		add(panel, c);
	}
}
