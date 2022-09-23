package org.mixindetector;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

public class MixinFile {

	private final File file;
	private boolean hasMixinsFolder;
	private Collection<String> fileNames = Collections.emptyList();

	public MixinFile(File file) {
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

	public void setMixinFileNames(Collection<String> mixinFiles) {
		this.fileNames = mixinFiles;
	}

	public Collection<String> getMixinFileNames() {
		return this.fileNames;
	}
}
