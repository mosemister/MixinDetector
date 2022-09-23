package org.mixindetector.gui.screen;

import org.mixindetector.MixinDetectorMain;
import org.mixindetector.MixinFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

public class ScanningScreen extends JPanel {

	private final File modsFolder;
	private final JPanel progressBarsPanel = new JPanel();

	private LinkedBlockingQueue<MixinFile> files = new LinkedBlockingQueue<>();


	public ScanningScreen(File modsFolder) {
		this.modsFolder = modsFolder;
		init();
	}

	private void init() {
		File[] mods = this.modsFolder.listFiles(pathname -> pathname.getName().endsWith(".jar"));
		if (mods == null) {
			throw new RuntimeException("Checks for mods were not performed");
		}
		setLayout(new GridLayout(1, 1));
		this.progressBarsPanel.setLayout(new GridLayout(mods.length, 1));
		add(new JScrollPane(this.progressBarsPanel));
		new Thread(() -> run(mods)).start();

	}

	private void run(File[] mods) {
		Stream.of(mods).parallel().forEach(modFile -> {
			JProgressBar bar = new JProgressBar();
			MixinFile mixinFile = new MixinFile(modFile);
			bar.setString(modFile.getName());
			bar.setStringPainted(true);
			progressBarsPanel.add(bar);
			progressBarsPanel.revalidate();
			progressBarsPanel.repaint();

			JarFile jar = null;
			try {
				jar = new JarFile(modFile);

				List<String> mixinFiles = jar.stream()
						.map(ZipEntry::getName)
						.filter(name -> name.endsWith(".json"))
						.filter(name -> {
							System.out.println(name);
							return name.startsWith("mixins.");
						})
						.collect(Collectors.toList());
				mixinFile.setMixinFileNames(mixinFiles);


				ZipEntry entry = jar.getEntry("org/spongepowered/common/mixin");
				System.out.println("Entry: " + entry);
				if (entry == null) {
					return;
				}
				System.out.println(this.modsFolder.getAbsolutePath());
				mixinFile.setHasMixinsFolder(true);
			} catch (IOException e) {
			}
			bar.setMaximum(100);
			bar.setMinimum(0);
			bar.setValue(100);
			this.files.add(mixinFile);
			try {
				if (jar != null) {
					jar.close();
				}
			} catch (IOException e) {

			}
		});

		JFrame frame = MixinDetectorMain.getInstance().getFrame();
		frame.setContentPane(new ResultsScreen(this.files));
		frame.repaint();
		frame.revalidate();
	}
}
