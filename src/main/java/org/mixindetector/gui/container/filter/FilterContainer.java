package org.mixindetector.gui.container.filter;

import org.mixindetector.MixinFile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterContainer extends JPanel{

    private final Collection<MixinFilter> allFilters;
    private final Map<MixinFilter, JPanel> applyingFilters = new HashMap<>();

    private final JPanel allFilterPanel;
    private final JPanel applyingFiltersPanel;

    private final Runnable onFilterChange;

    public FilterContainer(Runnable onFilterChange){
        this.allFilters = MixinFilters.getFilters();
        this.allFilterPanel = new JPanel();
        this.applyingFiltersPanel = new JPanel();
        this.onFilterChange = onFilterChange;
        init();
    }

    private void init(){
        this.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY), "Filter"));
        setLayout(new GridBagLayout());
        this.allFilterPanel.setLayout(new FlowLayout());
        this.applyingFiltersPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(this.applyingFiltersPanel, c);
        c.weighty = 0;
        c.gridy = 1;
        add(this.allFilterPanel, c);
        allFilters.stream().sorted(Comparator.comparing(MixinFilter::getName)).forEach(filter -> {
            JButton button = new JButton(filter.getName());
            button.addActionListener(e -> filter.onNew(FilterContainer.this));
            this.allFilterPanel.add(button);
        });
        setClosed(true);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setClosed(!isClosed());
            }
        };
        this.addMouseListener(adapter);
        this.allFilterPanel.addMouseListener(adapter);
        this.applyingFiltersPanel.addMouseListener(adapter);
    }

    public boolean isClosed(){
        return !this.allFilterPanel.isVisible();
    }

    public void setClosed(boolean closed){
        this.allFilterPanel.setVisible(!closed);
    }

    public boolean willFilter(MixinFile file){
        if(this.applyingFilters.isEmpty()){
            return false;
        }
        return this
                .applyingFilters
                .keySet()
                .parallelStream()
                .noneMatch(filter -> filter
                        .shouldKeep(file));
    }

    public Collection<MixinFile> apply(Collection<MixinFile> mixins){
        return mixins
                .parallelStream()
                .filter(this::willFilter)
                .collect(Collectors.toSet());
    }

    public boolean hasFilter(MixinFilter filter){
        return this.applyingFilters.containsKey(filter);
    }

    public void addFilter(MixinFilter filter, JPanel panel){
        this.applyingFilters.put(filter, panel);
        this.applyingFiltersPanel.add(panel);
        this.applyingFiltersPanel.repaint();
        this.applyingFiltersPanel.revalidate();
        this.onFilterChange.run();
    }

    public void removeFilter(MixinFilter filter){
        JPanel panel = this.applyingFilters.get(filter);
        this.applyingFilters.remove(filter, panel);
        this.applyingFiltersPanel.remove(panel);
        this.applyingFiltersPanel.repaint();
        this.applyingFiltersPanel.revalidate();
        this.onFilterChange.run();

    }
}
