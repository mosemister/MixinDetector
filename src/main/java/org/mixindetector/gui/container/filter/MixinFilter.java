package org.mixindetector.gui.container.filter;

import org.mixindetector.MixinFile;

public interface MixinFilter {

    String getName();
    boolean shouldKeep(MixinFile file);
    void onNew(FilterContainer container);
}
