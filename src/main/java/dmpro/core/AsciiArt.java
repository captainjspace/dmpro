package dmpro.core;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import dmpro.data.loaders.ResourceLoader;
import dmpro.utils.FileUtils;

public class AsciiArt implements ResourceLoader {

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private final String asciiDir = dataDirectory + "ascii-art";
	public final Map<String,String> art = new HashMap<String, String>();
	public AsciiArt() {
		load();
	}
	
	public void load() {
		try {
			for (Path path : FileUtils.listSourceFiles(FileSystems.getDefault().getPath(asciiDir)))
				art.put(path.getFileName().toString(), new String(Files.readAllBytes(path)));
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Cannot find ascii files", e);
		}
	}
	
	public static void main(String [] args) {
		AsciiArt a = new AsciiArt();
		a.art.entrySet().stream().forEach(p -> System.out.format("%s\n{\n%s\n}\n", p.getKey(), p.getValue()));
	}
}
