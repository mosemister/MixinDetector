package org.mixindetector.gui.container.nav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NavButtonContainer extends JLabel {

	private final Runnable onClick;

	public NavButtonContainer(String name, Runnable onClick) {
		super(name);
		this.onClick = onClick;
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.setRequestFocusEnabled(true);
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {

			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {
				NavButtonContainer.this.onClick.run();
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {
				NavButtonContainer.this.setBackground(Color.DARK_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {
				NavButtonContainer.this.setBackground(Color.LIGHT_GRAY);

			}
		});
	}

	public Runnable getOnClick() {
		return this.onClick;
	}
}
