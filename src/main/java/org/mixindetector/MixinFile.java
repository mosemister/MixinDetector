package org.mixindetector;

import java.io.File;

public class MixinFile {

    private final File file;
    private boolean hasMixinsFolder;

    public MixinFile(File file){
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean hasMixinsFolder() {
        return hasMixinsFolder;
    }

    public void setHasMixinsFolder(boolean hasMixinsFolder) {
        this.hasMixinsFolder = hasMixinsFolder;
    }
}
