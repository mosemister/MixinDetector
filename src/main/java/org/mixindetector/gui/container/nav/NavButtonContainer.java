package org.mixindetector.gui.container.nav;

import javax.swing.*;

public class NavButtonContainer extends JLabel {

	private final Runnable onClick;

	public NavButtonContainer(String name, Runnable onClick) {
		super(name);
		this.onClick = onClick;
	}

	public Runnable getOnClick() {
		return this.onClick;
	}
}
