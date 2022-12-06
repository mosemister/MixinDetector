package org.mixindetector.gui.container;

import org.mixindetector.MixinFile;
import org.mixindetector.gui.container.filter.FilterContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultsContainer extends JPanel {

	private final Collection<MixinFile> files;
	private final Collection<MixinFileContainer> displaying = new LinkedList<>();
	private final FilterContainer filterPanel;
	private final JPanel resultsPanel;


	public ResultsContainer(Collection<MixinFile> files) {
		this.files = files;
		this.resultsPanel = new JPanel();
		this.filterPanel = new FilterContainer(this::updateFilters);
		init();
	}

	public Collection<MixinFile> getAllFiles() {
		return this.files;
	}

	private void updateFilters(){
		Arrays
				.stream(this.resultsPanel.getComponents())
				.filter(com -> com instanceof MixinFileContainer)
				.forEach(this.resultsPanel::remove);

		List<MixinFileContainer> filtered = this
				.displaying
				.stream()
				.filter(container -> !this
						.filterPanel
						.willFilter(container.getMixinFile()))
				.collect(Collectors.toList());

		filtered.forEach(this.resultsPanel::add);
		this.resultsPanel.setLayout(new GridLayout(filtered.size(), 1));
	}

	private void init() {
		this.displaying.addAll(this.files.parallelStream().map(MixinFileContainer::new).collect(Collectors.toList()));
		this.resultsPanel.setLayout(new GridLayout(this.files.size(), 1));
		this.displaying.forEach(this.resultsPanel::add);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.filterPanel, c);
		c.gridy = 1;
		c.weighty = 1.0;
		this.add(new JScrollPane(this.resultsPanel), c);
	}
}
