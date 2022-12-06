package org.mixindetector.gui.screen;

import org.mixindetector.MixinDetectorMain;
import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

public class ScanningScreen extends JPanel {

	private final File modsFolder;
	private final JProgressBar bar;

	private LinkedBlockingQueue<MixinFile> files = new LinkedBlockingQueue<>();


	public ScanningScreen(File modsFolder) {
		this.modsFolder = modsFolder;
		this.bar = new JProgressBar();
		init();
	}

	private void init() {
		bar.setStringPainted(true);
		File[] mods = this.modsFolder.listFiles(pathname -> pathname.getName().endsWith(".jar"));
		if (mods == null) {
			throw new RuntimeException("Checks for mods were not performed");
		}
		bar.setMaximum(mods.length);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Searching for mixins in mods"), c);
		c.gridx = 1;
		c.weighty = 1.0;
		add(this.bar, c);
		Thread thread = new Thread(() -> run(mods));
		thread.start();

	}

	private void run(File[] mods) {
		Stream.of(mods).forEach(modFile -> {
			try {
				MixinFile mixinFile = new MixinFile(modFile);
				JarFile jar = null;
				try {
					jar = new JarFile(modFile);

					List<String> mixinFiles = jar.stream()
							.map(ZipEntry::getName)
							.filter(name -> name.endsWith(".json"))
							.filter(name -> name.contains("mixins."))
							.collect(Collectors.toList());
					mixinFile.setMixinFileNames(mixinFiles);


					ZipEntry entry = jar.getEntry("org/spongepowered/common/mixin");
					if (entry != null) {
						mixinFile.setHasMixinsFolder(true);

					}
				} catch (IOException e) {
				}
				bar.setValue(bar.getValue() + 1);
				this.files.add(mixinFile);
				try {
					if (jar != null) {
						jar.close();
					}
				} catch (IOException e) {

				}
			} catch (Throwable e) {
				JOptionPane.showInternalMessageDialog(null, e.getMessage(), Arrays.stream(e.getStackTrace())
						.map(element -> element.getLineNumber()
								+ " : "
								+ element.getClassName()
								+ " : "
								+ element.getMethodName())
						.collect(Collectors.joining("\n")), JOptionPane.ERROR_MESSAGE);
			}
		});

		new Thread(() -> {
			JFrame frame = MixinDetectorMain.getInstance().getFrame();
			frame.setContentPane(new ResultsScreen(this.modsFolder, this.files));
			frame.repaint();
			frame.revalidate();
		}).start();
	}
}
