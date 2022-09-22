package org.mixindetector.gui.container;

import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.function.Predicate;

public class ResultsContainer extends JPanel {

	private final Collection<MixinFile> files;
	private Predicate<MixinFile> filters;

	public static final Predicate<MixinFile> NO_FILTERS = (file) -> true;

	public ResultsContainer(Collection<MixinFile> files) {
		this.files = files;
		this.filters = NO_FILTERS;
		init();
	}

	private void init() {
		setLayout(new GridLayout(this.files.size(), 1));
		this.files.parallelStream().filter(this.filters).forEach(mf -> add(new MixinFileContainer(mf)));
	}

	public void setFilters(Predicate<MixinFile> predicate) {
		this.filters = predicate;
	}

	public void removeFilters() {
		this.filters = NO_FILTERS;
	}
}
