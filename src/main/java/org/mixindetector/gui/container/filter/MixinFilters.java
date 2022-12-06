package org.mixindetector.gui.container.filter;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public final class MixinFilters {

    public static final WithMixinFolderFilter WITH_FOLDER = new WithMixinFolderFilter();
    public static final WithMixinFilesFilter WITH_FILES = new WithMixinFilesFilter();
    public static final WithSpecificMixinFileFilter WITH_SPECIFIC_FILE = new WithSpecificMixinFileFilter();
    public static final WithCrashLogFilter CRASH_LOG = new WithCrashLogFilter();

    private MixinFilters(){
        throw new RuntimeException("Should not run");
    }

    public static Collection<MixinFilter> getFilters(){
        return Arrays
                .stream(MixinFilters.class.getDeclaredFields())
                .parallel()
                .filter(field -> Modifier.isFinal(field.getModifiers()))
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> MixinFilter.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (MixinFilter)field.get(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
